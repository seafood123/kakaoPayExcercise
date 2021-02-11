package org.kakaoPay.jpa.entity;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
@Entity
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Table(name="PICKUPTABLE")
public class PickUp {
    /**
     * 고유 아이디
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PICKUPID")
    private long id;

    /**
     * 뿌리기 Entity
     */
    @NonNull
    @ManyToOne(targetEntity = Seeding.class, fetch=FetchType.EAGER)
    @JoinColumn(name="TOKEN")
    private Seeding seeding;

    /**
     * 순번
     */
    @NonNull
    @Column(name="SEQ", nullable=false)
    private Integer seq;

    /**
     * 받은 금액
     */
    @NonNull
    @Column(name="AMOUNT", nullable = false)
    private Long amount;

    /**
     * 받은 사용자 id
     */
    @Column(name="USERID")
    private Long userId;

    /**
     * 받은 일시
     */
    @Column(name="PICKUP_DATE")
    private LocalDateTime pickupDate;

    /**
     *
     * @param userId 사용자 id
     */
    public void pickup(long userId){
        this.userId = userId;
        pickupDate = LocalDateTime.now();
    }

    /**
     * 받아갔는지
     * @return 받았다.
     */
    public boolean isReceived(){
        return Objects.nonNull(userId);
    }

    /**
     *
     * @return 안받아갔어
     */
    public boolean isNotReceived(){
        return !isReceived();
    }

}
