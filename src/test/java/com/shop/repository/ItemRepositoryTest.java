package com.shop.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import com.shop.entity.QItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.shop.entity.QItem.item;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext  //EntityManager 빈을 주입한다.
    EntityManager em;

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest(){
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }

    public void createItemList(){
        for(int i=1; i<=10; i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }


    @DisplayName("상품명 테스트")
    public void findByItemNmTest(){
        createItemList();
        List<Item> list = itemRepository.findByItemNm("1");

        for(Item item : list) {
            System.out.println(item);
        }
    }


    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByItemNmOrItemDetailTest(){
        createItemList();
        List<Item> list = itemRepository.findByItemNmOrItemDetail("1","1");

        for(Item item : list) {
            System.out.println(item);
        }
    }


    @DisplayName("Less than 테스트")
    public void findByLessThanTest(){
        this.createItemList();
        List<Item> list = itemRepository.findByPriceLessThan(10005);

        for(Item item : list){
            System.out.println(item);
        }
    }


    @DisplayName("가격별 정렬 테스트")
    public void findByPriceOrderByDescTest(){
        createItemList();
        List<Item> list = itemRepository.findByPriceOrderByPriceDesc(10005);

        for(Item item : list){
            System.out.println(item);
        }

    }

    @Test
    @DisplayName("검색 테스트")
    public void findByItemDetailTest(){
        this.createItemList();
        List<Item> detailList = itemRepository.findByItemDetail("설명");

        for(Item item : detailList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("검색 테스트")
    public void findByItemDetailTest2(){
        this.createItemList();
        List<Item> detailList = itemRepository.findByItemDetail2("설명");

        for(Item item : detailList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("Querydsl 조회 테스트1")
    public void queryDslTest(){
        this.createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em); //쿼리를 동적으로 생성한다. 생성자의 파라미터로는 EntityManager 객체를 넣어준다.
        QItem qitem = item;
        JPAQuery<Item> query = queryFactory.selectFrom(qitem)
                .where(qitem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qitem.itemDetail.like("%"+ "상품 상세 설명" + "%"))
                .orderBy(qitem.price.desc());

        List<Item> itemList = query.fetch();  //쿼리를 리스트로 반환한다.

        for(Item item : itemList){
            System.out.println(item);
        }
    }


    @Test
    @DisplayName("Querydsl 조회 테스트2")
    public void queryDslTest2(){
        this.createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em); //쿼리를 동적으로 생성한다. 생성자의 파라미터로는 EntityManager 객체를 넣어준다.
        QItem qitem = item;
        JPAQuery<Item> query = queryFactory.selectFrom(qitem)
                .where(qitem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qitem.itemDetail.like("%"+ "2" + "%"));

        Item item = query.fetchOne();  //쿼리를 리스트로 반환한다.

        System.out.println(item);

    }
}