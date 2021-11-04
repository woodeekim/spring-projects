package co.woody.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//InitializingBean, DisposableBean 초기에 사용했던 방식이고 지금은 잘 사용하지 않는다
public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출 + url" + url);

    }

    public void setUrl(String url) {
        this.url = url;
    }

    //서비스 시작시 호출
    public void connect() {
        System.out.println("connect : " + url);
    }

    public void call(String message) {
        System.out.println("call : " + url + " message = " + message);
    }

    //서비스 종료시 호출
    public void disconnect() {
        System.out.println("close " + url);
    }

    @PostConstruct
    public void init() throws Exception {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close() throws Exception {
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
