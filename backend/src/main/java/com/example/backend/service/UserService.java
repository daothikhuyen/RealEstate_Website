package com.example.backend.service;

import com.example.backend.dto.request.User.PasswordRequest;
import com.example.backend.dto.request.User.UserCreationRequest;
import com.example.backend.dto.request.User.UserUpdateRequest;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.enity.PasswordRestToken;
import com.example.backend.enity.Verifytokens;
import com.example.backend.enums.ActivityType;
import com.example.backend.enums.Role;
import com.example.backend.exception.AppException;
import com.example.backend.exception.ErrorCode;
import com.example.backend.enity.ActivityLogEntity;
import com.example.backend.enity.User;
import com.example.backend.mapper.UserMapper;
import com.example.backend.reponsitory.ActivityLogEntityResponsitory;
import com.example.backend.reponsitory.PasswordResetResponsitory;
import com.example.backend.reponsitory.UserRepository;
import com.example.backend.reponsitory.VerifytokensResponsitory;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor // lombok tạo các contructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) // đưa các final thành private nếu null
@Slf4j // log.
public class UserService {

    ActivityLogEntityResponsitory activityLogEntityRepo;
    PasswordEncoder passwordEncoder;
    UserRepository userRespository;
    VerifytokensResponsitory verifytokensResponsitory;
    PasswordResetResponsitory passwordResetResponsitory;
    UserMapper userMapper;

    @Autowired
    private JavaMailSender mailSender;

    private static final SecureRandom random = new SecureRandom();

    public String createUser(UserCreationRequest request){

        User user = userMapper.toUser(request);

        String password =passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRole(roles);

        ActivityLogEntity entity =new ActivityLogEntity();
        try {
            user = userRespository.save(user);

            if(user != null){
                entity.setEmail(user.getEmail());
                entity.setActivity(ActivityType.REGISTER);
                activityLogEntityRepo.save(entity);

                return CreateOTP(user.getEmail());
            }
            return null;

//                return userMapper.toUserResponse(user);
        }catch (DataIntegrityViolationException e){
            System.out.println("Lỗi: "+ e.getMessage());
            entity.setActivity(ActivityType.INVALID_REGISTER);
            entity.setEmail("_");
            activityLogEntityRepo.save(entity);
            throw new AppException(ErrorCode.USER_EXISTED);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    };

    public String CreateOTP(String email) throws MessagingException {
        String otp = String.valueOf(new Random().nextInt(8999) + 1000);

        String body = "Xin chào! Mã xác nhận phongtro.com của bạn là "+ otp;
        String subject = "Mã xác nhận";

        Verifytokens verifyOTP = new Verifytokens();
        verifyOTP.setEmail(email);
        verifyOTP.setToken(otp);
        verifytokensResponsitory.save(verifyOTP);

        sendEmail(email,body,subject);

        return otp;
    }

    public static String generateToken() {
        byte[] bytes = new byte[38]; // 38 bytes ~ 50 ký tự Base64
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).substring(0, 50);
    }

    public Boolean EmailForgetPassword(String email){

        try {
            System.out.println("email quên mật khau: "+ email);
            List<PasswordRestToken> tokens = passwordResetResponsitory.findByEmail(email);

            if(!tokens.isEmpty()){
                passwordResetResponsitory.deleteAll(tokens);
            }

            String token = generateToken();

            // Tạo nội dung HTML cho email
            String body = "<div style='font-family:Arial, sans-serif; max-width:500px; padding:20px; border:1px solid #ddd; border-radius:10px;'>"
                    + "<h2 style='color:#4CAF50; text-align:center;'>Quên Mật Khẩu</h2>"
                    + "<div>Xin chào,</div>"
                    + "<div>Bạn đang yêu cầu thay đổi mật khẩu tải khoản <span class='title_email'>"+email+"</span></div> "
                    + "<br>"
                    +" <div>Để cấp lại mật khẩu, Vui lòng click vào đường dẫn dưới đây: <a href='http://localhost:5173/account/reset_password/"+token+"' class='link'>Link xác nhận khôi phục mật khẩu.</a></div>"
                    + "<div>Mọi thắc mắc xin vui lòng liên hệ hòm mail <span class='title_email'>phongtrontot@gmail.com</span> để được hỗ trợ và giải đáp.</div>"
                    + "<div> Chúc các bạn có những trải nghiệm thú vị cùng <span class='link'>PhongTroTot.com</span></div>"
                    + "</div>";
            String subject = "Quên Mật Khẩu";


            LocalDate localDate = LocalDate.now();
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            PasswordRestToken passwordRestToken = new PasswordRestToken();
            passwordRestToken.setEmail(email);
            passwordRestToken.setToken(token);
            passwordRestToken.setCreatdAt(date);
            passwordResetResponsitory.save(passwordRestToken);

            sendEmail(email,body,subject);

            return true;
        }catch (Exception e) {
            e.getMessage();
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

//    Gửi email
    public void sendEmail(String email,String body, String subject) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("phongtrotot@gmail.com");
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(body, true); // Kích hoạt gửi HTML

        mailSender.send(message);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')") // kiểm tra method trước khi vào nếu thoả điều kiện thì thực hiện
    public List<UserResponse> getUsers() {
        log.info("Vào method getUsers");
        return userRespository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }

    // returnObject.email == authentication.name kiểm tra xem dữ liệu sau khi lâý có email trùng với email đang đăng nhập không
    public UserResponse getUser(Long id){

        return userMapper.toUserResponse(userRespository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public UserResponse getMyInfo(){
        try {
            var context = SecurityContextHolder.getContext();
            String email = context.getAuthentication().getName();

            User user = userRespository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

            return userMapper.toUserResponse(user);
        }catch (Exception e){
            throw  new UsernameNotFoundException(e.getMessage());
        }
    }

    @PostAuthorize("returnObject.email == authentication.name")
    public UserResponse updateUser(Long userId, UserUpdateRequest request) {

        User user = userRespository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user,request);

        return userMapper.toUserResponse(userRespository.save(user));

    }

    public String update_password(PasswordRequest request) {

        UserResponse userResponse = getMyInfo();

        User user = userRespository.findByEmail(userResponse.getEmail()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if(passwordEncoder.matches(request.getCurrent_pass(),user.getPassword())){

            String password =passwordEncoder.encode(request.getNew_pass());
            user.setPassword(password);
            userRespository.save(user);

            return "Thay đổi mật khẩu thành công";
        }else{
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

    }

    public String deleted_Account(){

        try {
            UserResponse userResponse = getMyInfo();
            User user = userRespository.findByEmail(userResponse.getEmail()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

            user.setIsDeleted(1);
            userRespository.save(user);

            return "Xoá tài khoản thành công";
        }catch (Exception e){
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Map<String, Object> list_user(int page) {

        try {
            Pageable paging = PageRequest.of(page -1,5);
            Page<User> page_users = userRespository.findAllByIsDeleted(0,paging);

            Map<String, Object> response = new HashMap<>();
            response.put("paginate", Map.of(
                    "total", page_users.getTotalElements(),
                    "per_page", page_users.getSize(),
                    "current_page", page_users.getNumber() + 1,
                    "last_page", page_users.getTotalPages(),
                    "from", page_users.hasContent() ? page_users.getContent().get(0).getId() : null,
                    "to", page_users.hasContent() ? page_users.getContent().get(page_users.getContent().size() - 1).getId() : null
            ));
            response.put("data", page_users.getContent());

            return response;
        } catch (Exception e) {
            e.getMessage();
            throw new AppException(ErrorCode.POST_NOT_EXISTED);
        }
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public String account_lock(Long userId) {

        try {
            User user = userRespository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

            String message = null;
            int is_activated = user.getIs_activated();

            if(is_activated == 1){
                user.setIs_activated(0);
                message = "Tài khoản đã bị khoá";
            }else{
                user.setIs_activated(1);
                message = "Tài khoản đã được mở khoá";
            }

            userRespository.save(user);

            return message;
        }catch (Exception e){
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public String destroyAccount(Long userId) {

        try {
            User user = userRespository.findByIdAndIsDeleted(userId,0).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

            String message = null;

            user.setIsDeleted(1);
            message = "Tài khoản đã bị xoá";

            userRespository.save(user);

            return message;
        }catch (Exception e){
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }


    public String is_activated(String email,String otp) {

        Optional<Verifytokens> checkOTP = verifytokensResponsitory.findByEmailAndToken(email,otp);

        if (checkOTP.isPresent()){
            Optional<User> finduser = userRespository.findByEmail(email);
            User user = finduser.get();
            user.setIs_activated(1); // Đặt trạng thái kích hoạt
            userRespository.save(user); // Lưu thay đổi

            checkOTP.ifPresent(verifytokensResponsitory::delete);

            return "Xác nhận thành công";
        }

        return null;
    }


    public Boolean ResetPassword(PasswordRequest request, String token) {

        System.out.println(request);
        Optional<PasswordRestToken> token_reset = passwordResetResponsitory.findByToken(token);

        if(token_reset.isPresent()){
            PasswordRestToken resetToken = token_reset.get();

            User user = userRespository.findByEmail(resetToken.getEmail())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

            String password =passwordEncoder.encode(request.getNew_pass());

            user.setPassword(password);
            userRespository.save(user);

            token_reset.ifPresent(passwordResetResponsitory::delete);

            return true;
        }else{
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }
}
