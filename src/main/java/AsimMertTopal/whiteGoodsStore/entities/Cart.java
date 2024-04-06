package AsimMertTopal.whiteGoodsStore.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Entity
@Table(name = "cart")
@Data
@RequiredArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<CartItems> cartItems;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
