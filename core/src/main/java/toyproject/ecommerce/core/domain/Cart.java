package toyproject.ecommerce.core.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Cart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    //==생성 메서드==//
    public static Cart createCart(Member member) {
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    }

    //==연관관계 메서드==//
    public void addCartItem(Item item, int cnt) {
        CartItem cartItem = CartItem.createCartItem(item, cnt);
        cartItems.add(cartItem);
        cartItem.setCart(this);
    }

    public int getTotalPrice() {
        return cartItems.stream()
                .mapToInt(CartItem::getTotalPrice)
                .sum();
    }
}
