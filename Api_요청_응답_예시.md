# 카테고리에 따른 전체 메뉴 요청
## 프론트 요청 예시
GET /api/products/detail?category=커피&branchId=1
- `category` = 카테고리
- `branchId` = 지점 기본키
## 백엔드 응답 예시
```json
[
    {
        "id": 1,
        "name": "아메리카노",
        "description": "진한 에스프레소와 물의 조화",
        "image_url": "/images/americano.jpg",
        "category": "커피",
        "price": 2000,
        "calories": 5,
        "carbohydrates": 0,
        "protein": 0,
        "fat": 0,
        "caffeine": 75,
        "options": [
            {
                "id": 1,
                "name": "HOT",
                "price": 0,
                "group": "TEMPERATURE",
                "multi_select": false
            },
            {
                "id": 2,
                "name": "ICE",
                "price": 500,
                "group": "TEMPERATURE",
                "multi_select": false
            }
        ],
        "unavailable": false,
        "temperature_option": "hot_and_ice"
    },
    {
        "id": 2,
        "name": "카페라떼",
        "description": "에스프레소와 부드러운 우유의 만남",
        "image_url": "/images/cafelatte.jpg",
        "category": "커피",
        "price": 2500,
        "calories": 100,
        "carbohydrates": 8,
        "protein": 6,
        "fat": 5,
        "caffeine": 75,
        "options": [
            {
                "id": 3,
                "name": "HOT",
                "price": 0,
                "group": "TEMPERATURE",
                "multi_select": false
            },
            {
                "id": 4,
                "name": "ICE",
                "price": 0,
                "group": "TEMPERATURE",
                "multi_select": false
            }
        ],
        "unavailable": false,
        "temperature_option": "hot_and_ice"
    }
]
```
- `id` = 제품 기본키
- `name` = 제품 이름
- `description` = 제품 상세 정보
- `image_url` = 제품 이미지 경로
- `category` = 제품 카테고리
- `price` = 제품 가격
- `calories, carbohydrates, protein, fat, caffeine` = 영양 정보
- `options` = 제품에 선택 가능한 옵션들
  - `id` = 옵션 기본키
  - `name` = 옵션 이름
  - `price` = 옵션 가격
  - `group` = 옵션 선택 그룹
  - `multi_select` = 다중 선택 가능 여부
- `unavailable` = 제품 품절 여부(혹은 지점장이 판매 금지 처분)
- `temperature_option` = 제품의 온도 선택 여부
# 휴대폰 번호로 회원 포인트 조회
## 프론트 요청 예시
GET /api/members/point/010-0000-0000
- `{phoneNumber}` = 회원의 휴대폰 번호
## 백엔드 응답 예시
```json
{
    "id": 1,
    "point": 5000
}
```
- `id` = 회원 기본키
- `point` = 회원 포인트
# 회원 ID로 쿠폰 조회
## 프론트 요청 예시
GET /api/members/{id}/coupons
- `{id}` = 회원의 ID (기본키)
## 백엔드 응답 예시
```json
[
    {
        "coupon_id": 1,
        "coupon_name": "아메리카노",
        "coupon_discount": 2000
    },
    {
        "coupon_id": 2,
        "coupon_name": "카페라떼",
        "coupon_discount": 2500
    }
]
```
- `coupon_id` = 쿠폰 기본키
- `coupon_name` = 쿠폰 이름 (해당 쿠폰으로 구매 가능한 제품의 이름)
- `coupon_discount` = 쿠폰 할인 금액 (해당 쿠폰으로 구매 가능한 제품의 가격)
# 상품 주문 생성
## 프론트 요청 예시
POST /api/orders
```json
{
    "products": [
        {
            "id": 1,
            "quantity": 2,
            "options": [
                {
                    "id": 1
                }
            ]
        },
        {
            "id": 2,
            "quantity": 1,
            "options": [
                {
                    "id": 4
                }
            ]
        }
    ],
    "member_id": 1,
    "coupon_id": 1,
    "membership_point": 1000,
    "payment_method": "CARD",
    "branch_id": 1
}
```
- `products`: 주문에 포함된 상품 목록.
    - `id`: 상품 ID.
    - `quantity`: 상품 수량.
    - `options`: 상품에 적용할 옵션 목록.
        - `id`: 옵션 ID.
- `member_id`: (선택 사항) 회원 ID. 쿠폰 및 멤버십 포인트 사용을 위해 필요. `null`이면 비회원 주문.
- `coupon_id`: (선택 사항) 사용할 쿠폰 ID.
- `membership_point`: (선택 사항) 사용할 멤버십 포인트.
- `payment_method`: 결제 방법 (`CREDIT_CARD`, `ACCOUNT_TRANSFER`, `SIMPLE_PAY`).
- `branch_id`: 주문이 이루어진 지점 ID.
## 백엔드 응답 예시
```json
{
    "branch_name": "선릉점",
    "order_number": 1
}
```
- `branch_name`: 주문이 이루어진 지점 이름.
- `order_number`: 생성된 주문 번호.
## 예외
- `USE_COUPON_OR_MEMBERSHIP_POINT_WITHOUT_MEMBER`: 회원 ID 없이 쿠폰이나 멤버십 포인트를 사용하려고 할 경우 발생.
- `INVALID_PAYMENT_METHOD`: 유효하지 않은 결제 방법을 사용하려고 할 경우 발생.
# 카테고리에 따른 간단한 메뉴 요청
## 프론트 요청 예시
GET /api/products/simple?category=커피&branchId=1
- `category` = 카테고리
- `branchId` = 지점 기본키
## 백엔드 응답 예시
```json
[
    {
        "id": 1,
        "name": "아메리카노",
        "image_url": "/images/americano.jpg",
        "price": 2000,
        "unavailable": false
    },
    {
        "id": 2,
        "name": "카페라떼",
        "image_url": "/images/cafelatte.jpg",
        "price": 2500,
        "unavailable": false
    }
]
```
- `id` = 제품 기본키
- `name` = 제품 이름
- `image_url` = 제품 이미지 경로
- `price` = 제품 가격
- `unavailable` = 판매 금지 처리 여부
# 상품 판매 금지 토글
## 프론트 요청 예시
POST /api/products/unavailable-toggle?productId=1&branchId=1
-  `productId` = 제품 기본키
-  `branchId` = 지점 기본키
## 백엔드 응답 예시
```json
{}
```
-   별도의 응답 본문은 없으며, 성공 시 HTTP 상태 코드 200 OK를 반환합니다.
## 예외
-   별도의 예외는 정의되어 있지 않지만, 요청 파라미터가 유효하지 않거나, 존재하지 않는 제품/지점 ID를 사용할 경우, 혹은 서버 내부 오류 발생 시 적절한 HTTP 상태 코드 (400 Bad Request, 404 Not Found, 500 Internal Server Error 등)와 함께 에러 메시지가 반환될 수 있습니다.
# 지점 기기 비밀번호 확인
## 프론트 요청 예시
GET /api/branches/{id}/check-device-pw?password={password}
- `id` = 지점 기본키
- `password` = 기기 비밀번호
## 백엔드 응답 예시
```json
true/false
```
- 응답 값은 비밀번호 일치 여부를 나타내는 boolean 값입니다. `true`이면 비밀번호가 일치하고, `false`이면 일치하지 않습니다.
# 지점 기기 비밀번호 변경
## 프론트 요청 예시
PATCH /api/branches/{id}/device-pw
```json
{
    "password": "{newPassword}"
}
```
- `id` = 지점 기본키
- `password` = 새로운 비밀번호
## 백엔드 응답 예시
```json
{}
```
- 응답 body는 비어 있으며, 성공 시 HTTP 상태 코드 200 OK를 반환합니다.
- 유효성 검사 실패 시 (예: 비밀번호가 비어있는 경우) 400 Bad Request 에러와 함께 에러 메시지가 반환됩니다.