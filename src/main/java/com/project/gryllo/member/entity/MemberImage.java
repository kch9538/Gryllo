package com.project.gryllo.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberImage {

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String uploadFileName;
    private String storeFileName;
    private String extension;

    public MemberImage(String uploadFileName, String storeFileName, String extension) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.extension = extension;
    }

    public static MemberImage createBasicImage() {
        return new MemberImage("basic-profile-image", "basic-profile-image", "png");
    }
    protected void setMember(Member member) {
        this.member = member;
    }

    public String getOriginalStoreFileName() {
        return storeFileName + "." + extension;
    }
}
