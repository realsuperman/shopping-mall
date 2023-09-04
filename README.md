# shopping-mall


### 🛍️ Project _ 쇼핑몰

✔️ **프로젝트 명** 

쇼핑몰 프로젝트

**✔️ 프로젝트 기간**

2023.08.14 ~ 2023.08.30

**✔️ 홈페이지 명**

롯데 ON

# 1. 팀 소개

| 이름 | 깃 주소 |
| --- | --- |
| 이우엽 | https://github.com/leewooyup |
| 전종민 | https://github.com/wakkpu |
| 최소영 | https://github.com/cso6005 |
| 최성훈 | https://github.com/realsuperman |
| 최창효 | https://github.com/qwerty1434 |

# 2. 프로젝트 소개

## 1. 주요 기능

[기능 정의서](https://www.notion.so/0c6829c3752a4f699fc776851450e1be?pvs=21) 

### 1. 메인 페이지 기능

- 헤더에서 카테고리로 보여주기
    - 드롭다운 방식의 헤더(롯데ON 메뉴 바 차용)
- 인기 상품 아이템 보기
    - 대분류를 기준으로 소분류 상품을 보여줌
    - 옆으로 넘겼을 때 캐러셀처럼 돌아옴
- 최신 상품 아이템 보기
    - 접속할 떄 마다 매번 새로운 카테고리의 상품이 보이게 됨

### 2. 회원가입/로그인 기능

- 벨리데이션 기능
    - 이메일, 비밀번호
- 비밀번호 암호화

### 3. 아이템 보기 관련 기능

- 재고가 N개 이하로 떨어졌을 때 상품의 개수를 알려줌
- 수량에서 선택하지 못함

### 4. 장바구니 관련 기능

- 이미 장바구니에 담긴 상품에 대해 장바구니 담기를 하면 INSERT가 아니라 UPDATE로 수량이 증가함
- 상품 지우기
- 오른쪽 CART TOTAL로 최종 주문 건 확인 가능
- 페이지네이션

### 5. 주문 관련 기능

- 주문 목록 확인 하기
- 주문 디테일 확인 하기
- 주문 취소하기

## 2. 기술 스택
- **OS**

<img src="https://img.shields.io/badge/Windows-0078D6?style=flat&logo=Windows&logoColor=white"> 

- **Back**

<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat&logo=Spring Boot&logoColor=white"> ![](https://img.shields.io/badge/Java-007396?style=flat&logo=OpenJDK&logoColor=white") <img src="https://img.shields.io/badge/Gradle-02303A?style=flat&logo=gradle&logoColor=white">

- **Front**

<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=JavaScript&logoColor=white">  ![](https://img.shields.io/badge/JSP-007396?style=flat&logo=OpenJDK&logoColor=white") <img src="https://img.shields.io/badge/jquery-0769AD?style=flat&logo=jquery&logoColor=white"> <img src="https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=HTML5&logoColor=white">

- **DB**

<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=MySQL&logoColor=white"> <img src="https://img.shields.io/badge/MyBatis-007054?style=flat&logo=MyBatis&logoColor=white">

- **Cloud**
  
<img src="https://img.shields.io/badge/Amazon AWS-232F3E?style=flat&logo=Amazon AWS&logoColor=white">  <img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=flat&logo=Amazon EC2&logoColor=white"> <img src="https://img.shields.io/badge/Amazon RDS-527FFF?style=flat&logo=Amazon RDS&logoColor=white"> 

- **기타**
  
<img src="https://img.shields.io/badge/RestTemplate-6DB33F?style=flat&logo=Spring Boot&logoColor=white">  <img src="https://img.shields.io/badge/SpringSecurity-DB33F?style=flat&logo=springsecurity&logoColor=white"> <img src="https://img.shields.io/badge/AJAX-5A29E4?style=flat&logo=AJAX&logoColor=white"> <img src="https://img.shields.io/badge/kakaoAPI-FFCD00?style=flat&logo=kakao&logoColor=white"> <img src="https://img.shields.io/badge/intellijidea-000000?style=flat&logo=intellijidea&logoColor=white"> <img src="https://img.shields.io/badge/Git-F05032?style=flat&logo=Git&logoColor=white"> <img src="https://img.shields.io/badge/GitHub-181717?style=flat&logo=GitHub&logoColor=white">

## 3. ERD

![Untitled](https://github.com/realsuperman/shopping-mall/assets/25142537/b89db01b-0b66-4304-ab48-f83c601653e9)

## 4. 시스템 구조

- 사용자 요청이 들어옴
- Tomcat이 ServletRequest , ServletResponse를 생성해 적절한 Servlet에게 전달
- 필터가 호출됨
- DispatcherServlet이 호출됨
- DispatcherServlet의 생성자 호출 시점에 urlMapper에 값을 채움
- DispatcherServlet의 service에서 uri, method, path를 추출하고 path를 key로 urlMapper에 값이 존재하는지 확인
- 존재하면 `invokeAppropriateMethod`호출


# 3. 고민한 부분

### 1. 트랜잭션

❓**고민**

- 초기 트랜잭션의 순서
    1. update cargo → 결제 → 재고 확인 → insert order_set, order_detail, delete cart_item
    2. 결제 → 재고 확인 → update cargo → insert order_set, order_detail, delete cart_item
        
        → 2번으로 정하게 됨.
        

⭕ **해결**

- 카카오 api 문서 확인 이후의 트랜잭션 순서 : 결제가 한번의 요청-응답으로 끝나는 것이 아니었음
    
    ⇒ **결제 요청 → 재고 확인 → update cargo, insert order_set, order_detail, delete cart_item → 결제 승인 요청**
    

### 2. 세션 유지

❓**고민**

- 결제를 할 때 카카오 관련해서 페이지를 강제로 open시키거나, redirect하는 작업이 필요한 상황
- sessionStorage에 값을 담고, session에 값을 담는 경우가 발생했다.

**⭕ 해결**

- CSR의 경우 일반적으로 쉽게 해결할 수 있는 문제이지만 SSR로 작업을 진행하다보니 이러한 어려움을 겪게 되었음.
- SSR로 진행했더라도 프론트엔드 쪽의 설계를 견고하게 가져갔다면 문제가 발생하지 않았을 거 같음.

### 3. PRG 패턴

❓**고민**

- item insert부분에서 form을 submit했을 때 page가 이동하는 데 특정 값이 validation을 통과하지 못했을 때, validation에 통과한 값만 남아있어야 한다.
    - 백엔드에서 Validation패키지를 이용해 사용자 입력을 검증하는 기능을 구현했는데,  Validation 코드를 Controller에 섞을지, Controller를 호출하기 전에 Validation을 별도로 실행할지에 대한 고민이 있었음.

⭕ **해결**

- Session객체에 값을 넣는 작업은 너무 일이 커지는 것이라 생각함.
- validation을 따로 빼면 페이지 forward가 발생하지 않아 화면에 다시 값을 채우는 걸 고민하지 않아도 된다.
- 스프링쓰면 해결될거라 굳이 복잡하게 하지는 않았다.

### 4. 리플렉션을 도입할지에 대한 고민

❓**고민**

- 리플렉션을 안썼으면 모든 코드가 request의 메서드(GET,POST)를 직접 까서 확인해야 함.
    - 컨트롤러는 요청을 받아 뭔가를 호출해주는 코드만 있어야 하는데 그 외에 중복적인 작업이 많이 필요할 거란 생각이 들었음.
- 하지만 일반적으로 리플렉션은 권장되지 않는 기능이기도 함.
    1. 리플렉션을 사용하면 private하게 선언한 값들에도 모두 접근을 할 수 있다.
    2. 리플렉션을 사용하면 컴파일 타임에 체크가 불가능하고 런타임 시점에 문제를 확인할 수 있다.

⭕ **해결**

- 그래서 리플렉션은 정말 필요한가를 고민해봐야 한다. 또한 쓸꺼면 테스트코드를 깐깐하게 작성해야 한다.
    - 정말 필요한지에 대한 고민: 컨트롤러에 의존적인 중복코드가 모두 들어가는 것보다 리플렉션을 한번 쓰는게 이득이라 생각했다.
    - 테스트 코드를 작성했는지: GET, POST에 대한 테스트 및 검증을 진행했다.

### 5. 쿼리 효율화에 대해

❓**고민**

- for문 안에 for문을 도는 코드

```java
public List<Long> getInSufficientItemIds(SqlSession sqlSession, List<OrderItemDto> orderItemDtoList) {
    List<Long> result = new ArrayList<>();
    for(OrderItemDto orderItemDto: orderItemDtoList) {
        if(cargoDao.selectCountByItemId(sqlSession, orderItemDto.getItemId()) < orderItemDto.getItemQuantity()) {
            result.add(orderItemDto.getItemId());
        }
    }
    return result;
}
```

- service단에서 list에 객체 각각을 매퍼에 줘서 일일이 DB를 N번 찔러야 한다.
- mapper에게 list를 줘서 동적쿼리로 DB를 한번만 접근하고 DB에서 루프를 돌려서 결과를 얻게 설계하는 방법을 고민했다.
- union1000번 vs DB 찔러보기 1000번 → 시간차이가 없다면 union을 쓸듯: for loop같은건 아랫단으로 넣는걸 선호 // IO보다는 union이 부하가 덜하지 않을까? // 근데 DB에 엄청 큰 부담아니냐

### 6.  jstl 문법

❓**고민**

jstl에서 지원해주지 않은 문법(break)으로 문제가 발생함.

⭕ **해결**

기존의 프론트 작업을 백엔드에서 가공해서 넘기도록 진행함.

### 7. Servlet 기본 스팩에 대해

- doDELETE에서 임의로 doGET을 호출해서 doGET의 redirect를 호출하는 로직을 기대했으나 원하는대로 동작하지 않았다. → 애초에 WebServlet은 doDELETE의 리다이렉션 기능을 지원해주지 않는거 같다는 중간결론을 내림.

- ajax의 PUT요청으로 데이터를 보냈을 때 WebServlet에서 request.getParameter()를 쓰면 값을 못읽어옴
    - getParameter는 원래 GET의 PathVariable속 데이터를 읽는 용도이지만 servlet이 POST만 특별히 getParameter()로 body를 읽어올 수 있게 기능을 지원해 주는 거다. 하지만 PUT은 지원해주지 않기 때문에 PUT의 body 데이터를 request.getParameter()로 읽어오는 건 불가능.

# 4. 회고록

### 이우엽 
- 변수이름을 고민을 많이 하는게 좋다는 생각을 했다.
- jsp에서 $("태그명").val() 방식으로 값을 가져오는 경우가 많았는데 // 증복이 될까봐 매번 변수이름을 안겹치게 정한게 나중에는 독이 됐다.
- 프로젝트를 통해 RESTFUL 메서드에 대한 정확한 이해와 적용을 할 수 있었다.
- ajax적용을 많이 해보면서 ajax에 대해 이해할 수 있었다.
- 중복코드에 대한 공통화를 고려했었더라면 더 좋았을 거 같다.
- 페이지네이션을 스프링 부트에서는 만들어진 걸 간단히 가져다썼는데 직접 구현해보면서 내부 동작 원리를 잘 생각해 볼 수 있어서 좋았다.
- 디버깅을 해보면서 문제의 원일을 찾는 역량이 늘었다.
- 페이지네이션을 자바로 구현할 생각만 했는데 다른 분은 JS로 하신걸 보고 더 쉽게 가능하다는 걸 알게 됐다.

### 전종민
- 프로젝트 초반에 JSP와 HttpServlet의 동작 방식을 파악하는 데 애를 먹었다. 직관적으로 request에 값을 넣어서 준다는게 도저히 이해가 안갔었다. jsp에 이 값을 넣어 그려달라는 요청의 의미로 request라고 이해했다.
- HttpServlet의 doPost 내부에서는 getParameter를 사용할 수 있도록 서블릿 컨테이너가 처리해주지만, doPut 내부에서는 getParameter를 사용할 수 있도록 서블릿 컨테이너가 처리해주지 않아 디버깅에 애를 먹은 적이 있다. ajax의 data 영역에 담아 보낸 데이터를 읽어올 때는 항상 getInputStream으로 받도록 하자.
- 1년 넘게 잊고 있던 Post-Redirect-Get 패턴을 다시금 상기할 수 있었다.
- 도메인 단위로 설계는 했는데, 메서드 설계나 dto 단계까지 설계하지 못해 단일 행 mapper + 서비스 단위의 for-loop으로 코드를 작성하거나, 1, 2개 필드만 다른 중복된 dto를 만들어 사용하게 됐다. 설계에 충분한 시간을 들여 작은 단위까지 설계해야겠다는 생각이 들었다.
- 외부 API를 사용할거라면 DB 설계 시부터 이를 고려해야 한다는 생각이 들었다.
- 프로젝트 초반에는 dao와 service 테스트 코드도 잘 작성했지만 프로젝트를 진행하다 보니 테스트 코드를 작성하지 못했다.
- Spring과 CSR의 소중함을 느꼈다. jsp 파일 내에 script 넣고, style 넣고 개판으로 짰다.
- 업무가 다소 과중했다…

### 최소영
- 쇼핑몰 프로젝트를 처음 진행하는 만큼 해당 도메인에서 어떤 설계, 어떤 개발이 이뤄져야 하는지 알게 되어 좋았습니다. 
- 개발 전 구조화 설계가 얼마 만큼 중요한지, 또, 어떻게 이뤄져야 하는지 알게 되었습니다. 
- 처음 기획, 설계에서 달라진 부분 또, 구현하지 못한 부분도 있어, 아쉽기도 합니다. 

### 최성훈
- 백엔드 설계는 괜찮게 했는데 프론트 설계가 아쉬웠다.
ex) 공통페이지에 header footer만 두고 jsp자체를 끼워넣게 설계했으면 더 좋았을건데 그러지 못했다.
카카오쪽에서 값을 몇페이지에 걸쳐 유지했어야하는 상황이 발생했는데 해당 방법을 미리 가져갔다면 이 문제를 피할 수 있었는데 그러지 못해 Session Storage를 쓰는 상황이 발생했다.
- 원래 이런식의 설계가 일반적인데 왜 이렇게 하는건지를 이제 알았다.
- 전체적인 개발 스케줄을 고려하지 못하고 기능을 확장하면서 조금의 아쉬운 점들이 생겼다.
- 원했던 공통화 기능은 잘 작성했고 잘 동작해서 개인적으로는 만족스럽다.

### 최창효
- 이론적으로 알고 있던 내용도 막상 직접 개발할때는 지켜지지 않는 게 많다.
