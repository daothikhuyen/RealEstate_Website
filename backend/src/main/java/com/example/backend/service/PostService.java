package com.example.backend.service;

import com.example.backend.dto.request.PostRequest;
import com.example.backend.dto.request.Search.LocationRequest;
import com.example.backend.dto.request.Search.PriceAreaRequest;
import com.example.backend.dto.request.Search.SearchRequest;
import com.example.backend.dto.response.LocationResponse;
import com.example.backend.dto.response.PostDetailResponse;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.dto.specification.PostSpecification;
import com.example.backend.enity.*;
import com.example.backend.exception.AppException;
import com.example.backend.exception.ErrorCode;
import com.example.backend.mapper.PostMapper;
import com.example.backend.reponsitory.*;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor // lombok tạo các contructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) // đưa các final thành private nếu null
@Slf4j
public class PostService {

    UserService userService;
    PostRepository postRepository;
    ImageRepository imageRepository;
    VideoRepository videoRepository;
    ExtensionRepository extensionRepository;
    UserRepository userRepository;

    private final PostMapper postMapper;

    @Autowired
    private JavaMailSender mailSender;

    public List<PostDetailResponse> getAll(){

        List<Post> posts = postRepository.findAllByOrderByIdDesc();

        return responseListPost(posts);
    }

    public Boolean create_post(PostRequest request) {

        System.out.println("Bài đăng: "+ request);

        Post post = postMapper.toPost(request.getPostData());
        List<Images> images = request.getImages();
        List<Videos> videos = request.getVideos();
        List<Extensions> extensions = request.getExtensions();

        try {
            postRepository.save(post);
            Long postId = post.getId();  // Lấy id sau khi lưu

            // Sử dụng postId để thiết lập cho các đối tượng images, videos, và extensions
            if(images != null){
                for (Images image : images) {
                    image.setPostId(postId);  // Giả sử Images có thuộc tính postId
                    imageRepository.save(image);  // Lưu từng ảnh vào DB
                }
            }

            if(videos != null){
                for (Videos video : videos) {
                    video.setPostId(postId);  // Giả sử Videos có thuộc tính postId
                    videoRepository.save(video);  // Lưu từng video vào DB
                }
            }

            if(extensions != null){
                for (Extensions extension : extensions) {
                    extension.setPostId(postId);  // Giả sử Extensions có thuộc tính postId
                    extensionRepository.save(extension);  // Lưu từng extension vào DB
                }
            }

            return true;
        }catch (DataIntegrityViolationException e){
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);

        }

    }

//    lấy ra tất cả bài đăng của user đang đăng nhập
    public Map<String, Object> getMyPosts(int page) {

        try {

            UserResponse user = userService.getMyInfo();

            Pageable paging = PageRequest.of(page -1,5);
            Page<Post> pagePosts = postRepository.findByUserIdAndIsDeleted(user.getId(),0,paging);

            Map<String, Object> response = new HashMap<>();
            response.put("paginate", Map.of(
                    "total", pagePosts.getTotalElements(),
                    "per_page", pagePosts.getSize(),
                    "current_page", pagePosts.getNumber() + 1,
                    "last_page", pagePosts.getTotalPages(),
                    "from", pagePosts.hasContent() ? pagePosts.getContent().get(0).getId() : null,
                    "to", pagePosts.hasContent() ? pagePosts.getContent().get(pagePosts.getContent().size() - 1).getId() : null
            ));
            response.put("data", pagePosts.getContent());

            return response;
        } catch (Exception e) {
            e.getMessage();
            throw new AppException(ErrorCode.POST_NOT_EXISTED);
        }
    }

// gửi email
    public void sendEmail(String email,String body, String subject) throws MessagingException {

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

    helper.setFrom("phongtrotot@gmail.com");
    helper.setTo(email);
    helper.setSubject(subject);
    helper.setText(body, true); // Kích hoạt gửi HTML

    mailSender.send(message);
}

    public String deletedPost(Long postId){

        try {

            Optional<Post> post = postRepository.findById(postId);

            Optional<User> user = userRepository.findById(post.get().getUserId());

            if(post.isPresent()){
                Post p = post.get();
                p.setIsDeleted(1);
                postRepository.save(p);

                String body = "<div style='font-family:Arial, sans-serif; max-width:500px; padding:20px; border:1px solid #ddd; border-radius:10px;'>"
                        + "<h2 style='color:#4CAF50; text-align:center;'>Thông Báo Xoá Bài Đăng</h2>"
                        + "<div>Xin chào,</div>"
                        + "<div >Bài đăng <span style='color:#4CAF50; text-align:center;'>'"+ post.get().getTitle() +"' </span> đã được xoá </div>"
                        + "<div>Mọi thắc mắc xin vui lòng liên hệ hòm mail <span class='title_email'>phongtrontot@gmail.com</span> để được hỗ trợ và giải đáp.</div>"
                        + "</div>";
                String subject = "Huỷ bài đăng";

                sendEmail(user.get().getEmail(),body,subject);

                return "Xoá bài thành công";
            }else{
                throw  new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
            }

        }catch (Exception e){
            e.getMessage();
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

    }

    public PostDetailResponse showPosts(Long postId) {

        Optional<Post> posts = postRepository.findById(postId);

        if(posts.isPresent()){
            return responseListPost(List.of(posts.get())).get(0);
        }else{
            throw new AppException(ErrorCode.POST_NOT_EXISTED);
        }

    }

    public String editPost(Long postId, PostRequest request) {
        List<Long> list_id ;
        try {
            Post post = postRepository.findById(postId).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_EXISTED));

            if(post != null){
                post = request.getPostData();


                postRepository.save(post);

                if(request.getImages().size() > 0){
                    list_id = new ArrayList<>();
                    for (Images image : request.getImages()){
                        image.setPostId(postId);
                        Images i = imageRepository.save(image);
                        Long newImageId = i.getId();

                        if(i.getId() != null){
                            list_id.add(i.getId());
                        }
                    }

                    if (!list_id.isEmpty()) {
                        imageRepository.deleteImagesNotInIds(postId, list_id);
                    }

                }else{
                    imageRepository.deleteByPostId(postId);
                }

                if(request.getVideos().size() > 0){

                    list_id = new ArrayList<>();
                    for (Videos video : request.getVideos()){

                        video.setPostId(postId);
                        Videos v = videoRepository.save(video);

                        if(v.getId() != null){
                            list_id.add(v.getId());
                        }
                    }

                    if (!list_id.isEmpty()) {
                        videoRepository.deleteVideosNotInIds(postId, list_id);
                    }
                }else{

                    videoRepository.deleteByPostId(postId);
                }

                if(request.getExtensions().size() > 0){
                    list_id = new ArrayList<>();
                    for (Extensions extension : request.getExtensions()){
                        extension.setPostId(postId);
                        Extensions e = extensionRepository.save(extension);

                        if(e.getId() != null){
                            list_id.add(e.getId());
                        }
                    }

                    if (!list_id.isEmpty()) {
                        extensionRepository.deleteExtensionsNotInIds(postId,list_id);
                    }
                }else{
                    extensionRepository.deleteByPostId(postId);
                }
                
                return "Cập nhật thành công";
            }else{
                return "Cập nhật thất bại";
            }


        }catch (Exception e){
            e.printStackTrace();
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public Map<String, Object> list_SearchSuggestion(SearchRequest request){
        Map<String, Object> response = new HashMap<>();
        try {
            Page<Post> result ;


            Pageable pageable = PageRequest.of(0, 10);

            // Tìm kiếm theo tiêu đề
            result = postRepository.findTitleSuggestions(request.getInput(),pageable);
            // Nếu không có kết quả, tìm kiếm theo địa chỉ
            if (result.isEmpty()) {
                result = postRepository.findFullAddressSuggestions(request.getInput(),pageable);

            }

            response.put("data", result.getContent());

            return response;
        } catch (Exception e) {
            log.error("Error while fetching search suggestions: " + e.getMessage());
        }

        return response;

    }

    public List<PostDetailResponse> searchInput_All(SearchRequest request) {

        try {
            List<Post> posts = postRepository.searchInputAll(request.getInput());

            return responseListPost(posts);
        }catch (Exception e){
            e.getMessage();
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public List<PostDetailResponse> searchByLocation_Id(LocationRequest request) {

        List<Post> posts = null;
        if(request.getDistrictId() == 0){
            posts = postRepository.findByProvinceId(request.getProvinceId());
        }else{
            if(request.getWardId() == 0){
                posts = postRepository.findByProvinceIdAndDistrictId(request.getProvinceId(),request.getDistrictId());
            }else{
                posts = postRepository.findByProvinceIdAndDistrictIdAndWardId(request.getProvinceId(),request.getDistrictId(),request.getWardId());
            }
        }

        return responseListPost(posts);
    }

    public List<PostDetailResponse> searchPriceOrArea(PriceAreaRequest request) {

        Specification<Post> specification = Specification.where(null);
        if (request.getUnder() != 0 && request.getOn() != 0){
            specification = specification.and(PostSpecification.hasPriceOrAreaBetween(request.getName(),request.getOn(),request.getUnder()));
        }else{
            if(request.getOn() != 0){
               specification = specification.and(PostSpecification.hasPriceOrAreaGreaterThan(request.getName(),request.getOn()));
            }else{
                specification = specification.and(PostSpecification.hasPriceOrAreaLessThan(request.getName(),request.getUnder()));
            }
        }

        return responseListPost(postRepository.findAll(specification));
    }

    private List<PostDetailResponse> responseListPost(List<Post> posts) {
//hi
        try {
            List<PostDetailResponse> list_posts = new ArrayList<>(); ;

            if(posts != null && !posts.isEmpty()) {
                list_posts = posts.stream().map(resPost -> {
                    PostDetailResponse response = new PostDetailResponse();

                    response.setPostData(resPost);

                    UserResponse user = userService.getUser(resPost.getUserId());

                    response.setUser(user);

                    List<Images> images = imageRepository.findByPostId(resPost.getId());
                    response.setImages(images);

                    List<Videos> videos = videoRepository.findByPostId(resPost.getId());
                    response.setVideos(videos);

                    List<Extensions> extensions = extensionRepository.findByPostId(resPost.getId());
                    response.setExtensions(extensions);

                    LocationResponse locationResponse = new LocationResponse(resPost.getProvinceId(), resPost.getDistrictId(), resPost.getWardId(), resPost.getTitle());
                    response.setLocationResponse(locationResponse);

                    return response;

                }).toList();
            }

            return list_posts;
        }catch (Exception e){
            e.getMessage();
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    private PostDetailResponse setUp_PostResponse(Post resPost){

        PostDetailResponse response = new PostDetailResponse();

        response.setPostData(resPost);

        UserResponse user = userService.getUser(resPost.getUserId());

        response.setUser(user);

        List<Images> images = imageRepository.findByPostId(resPost.getId());
        response.setImages(images);

        List<Videos> videos = videoRepository.findByPostId(resPost.getId());
        response.setVideos(videos);

        List<Extensions> extensions = extensionRepository.findByPostId(resPost.getId());
        response.setExtensions(extensions);

        LocationResponse locationResponse = new LocationResponse(resPost.getProvinceId(),resPost.getDistrictId(), resPost.getWardId(), resPost.getTitle());
        response.setLocationResponse(locationResponse);

        return  response;
    }

}
