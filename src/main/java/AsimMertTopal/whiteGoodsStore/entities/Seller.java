package AsimMertTopal.whiteGoodsStore.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Table(name = "sellers")
@Data
@RequiredArgsConstructor
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private Long id;


    @Column(name = "tax_number",unique = true,nullable = false)
    private Long taxNumber;

    @Column(name = "company_name",unique = true,nullable = false)
    private String companyName;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", unique = true)
    private User user;



    @Column(name = "total_revenue", nullable = false, columnDefinition = "double default 0")
    private double totalRevenue;

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Product> products;


    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<OrderItems> orderItems;



}
