package co.woody.singleton;

public class StatefulService {
    /* 공유되는 필드를 사용하게 되면 값이 수정될 수 있기 때문에 지역변수를 사용하자*/
//    private int price;

    private String name;

    public int order(String name, int price) {
        return price;
    }
}
