package com.example.backend.reponsitory;

import com.example.backend.enity.Extensions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExtensionRepository extends JpaRepository<Extensions,Long> {

    List<Extensions> findByPostId(Long post_id);

    @Transactional
// đảm bảo rằng các thao tác sẽ được thực thi trong một giao dịch, tránh các lỗi liên quan đến việc thay đổi dữ liệu ngoài giao dịch.
    @Modifying
    // cho Spring Data biết rằng đây là một câu lệnh thay đổi dữ liệu, không phải câu lệnh SELECT thông thường.
    @Query("DELETE FROM Extensions p WHERE postId = :postId AND  p.id NOT IN :ids")
    int deleteExtensionsNotInIds(@Param("postId") Long postId, @Param("ids") List<Long> ids);

    @Transactional
    @Modifying
    @Query("DELETE FROM Extensions p WHERE postId = :postId ")
    int deleteByPostId(@Param("postId") Long postId);
}
