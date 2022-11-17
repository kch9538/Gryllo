package com.project.gryllo.notification.entity;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NotiRepository extends JpaRepository<Noti, Integer>{
	
	@Modifying
	@Query(value = "INSERT INTO Noti(fromUserId, toUserId, notiType, createDate) VALUES(?1, ?2, ?3, now())", nativeQuery = true)
	int mSave(int fromUserId, int toUserId, String notiType);
	
	
	List<Noti> findByToUserId(int loginUserId);
	
	// 최근 5개 알림
	@Query(value = "select * from Noti where toUserId = ?1 order by createDate desc limit  0, 5;", nativeQuery = true)
	List<Noti> mNotiForHeader(int loginUserId);
}
