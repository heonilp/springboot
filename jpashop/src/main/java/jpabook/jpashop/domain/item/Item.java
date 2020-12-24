package jpabook.jpashop.domain.item;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;
import jpabook.jpashop.domain.Category;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//strategy전략 JOINED , SINGLE_TABLE 한테이블(싱글테이블 전략)
@DiscriminatorColumn(name = "dtype") //타입
@Getter @Setter
//추상 클래스 구현체를 들구해야함
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

    // 비지니스 로직 추가

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }



}