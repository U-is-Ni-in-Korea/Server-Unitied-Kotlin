![image](https://github.com/U-is-Ni-in-Korea/Server-Unitied-Kotlin/assets/45380072/8b8a96e9-4225-493f-b9f2-a02d350351a0)
해당 프로젝트는 SOPT 32th APPJAM 프로젝트의 kotlin + Spring Boot 적용 및 Architecutre 설계 프로젝트 입니다.
## TOC
1.[Project Info](#Project-Info)</br>
2.[Project Service Info](#Project-Service-Info)</br>

---
</br></br>
## Project Info
![image](https://github.com/U-is-Ni-in-Korea/Server-Unitied-Kotlin/assets/45380072/a7b1973b-a112-4a13-88e7-85cf032c7d39)
해당 프로젝트의 각 모듈은 CleanArchitecture 의 규칙을 이용합니다.
> module-infrastructure

Clean Architecture 의 Interface Adapters 영역으로 실제 Web, UI, DB, ExternalInterface 와 연결되는 부분으로 설계하였습니다.</br>
Framework 혹은 UI 와 같은 외부적인 요인을 내부 UseCase 에서 사용하는 모델로 변경하거나 혹은 그 반대의 어뎁터 역할로 설계하였습니다.

> module-usecase

Clean Architecture 의 UseCase 영역과 동일한 역할을 수행하도록 설계하였습니다.</br>
비즈니스 시나리오와 같은 수단을 구현하고 이를 수행하는 역할로 설계하였습니다.

> module-domain

Clean Architecture 의 Entities 영역과 동일한 역할을 수행하도록 설계하였습니다.</br>
도메인에 해당하는 로직을 구현하도록 설계하였습니다. </br>
Kotlin 이외 의존성을 추가하지 않는 것을 목표로 설계하였습니다.

> 의존 방향

infrastructure -> usecase -> domain 의 방향으로 의존할 수 있도록 설계하였습니다.</br>
내부에 존재하는 모듈은 외부의 의존하지 않으며 외부의 요청에 따라 동작할 수 있도록 DIP 를 적용하여 interface로 명시한 기능만을 수행할 수 있도록 구성하였습니다.</br>
각 모듈의 변경이 다른 모듈의 변경을 최소화하고 테스트하기 용이하게 만들어 다양한 테스트 코드를 작성하기 위해 이와 같이 구성하게 되었습니다.
</br></br>
## Project Service Info

<div >
	<code><img width="50" src="https://user-images.githubusercontent.com/25181517/117201156-9a724800-adec-11eb-9a9d-3cd0f67da4bc.png" alt="Java" title="Java"/></code>
	<code><img width="50" src="https://user-images.githubusercontent.com/25181517/183891303-41f257f8-6b3d-487c-aa56-c497b880d0fb.png" alt="Spring Boot" title="Spring Boot"/></code>
	<code><img width="50" src="https://user-images.githubusercontent.com/25181517/190229463-87fa862f-ccf0-48da-8023-940d287df610.png" alt="Lombok" title="Lombok"/></code>
	<code><img width="50" src="https://user-images.githubusercontent.com/25181517/183892787-bca94a0e-ffcb-4eeb-8137-e0fc4e446c25.png" alt="Groovy" title="Groovy"/></code>
	<code><img width="50" src="https://user-images.githubusercontent.com/25181517/183896128-ec99105a-ec1a-4d85-b08b-1aa1620b2046.png" alt="MySQL" title="MySQL"/></code>
	<code><img width="50" src="https://user-images.githubusercontent.com/25181517/183896132-54262f2e-6d98-41e3-8888-e40ab5a17326.png" alt="AWS" title="AWS"/></code>
	<code><img width="50" src="https://user-images.githubusercontent.com/25181517/192108372-f71d70ac-7ae6-4c0d-8395-51d8870c2ef0.png" alt="Git" title="Git"/></code>
	<code><img width="50" src="https://user-images.githubusercontent.com/25181517/192108374-8da61ba1-99ec-41d7-80b8-fb2f7c0a4948.png" alt="GitHub" title="GitHub"/></code>
	<code><img width="50" src="https://user-images.githubusercontent.com/25181517/192108890-200809d1-439c-4e23-90d3-b090cf9a4eea.png" alt="InteliJ" title="InteliJ"/></code>
	<code><img width="50" src="https://user-images.githubusercontent.com/25181517/192109061-e138ca71-337c-4019-8d42-4792fdaa7128.png" alt="Postman" title="Postman"/></code>
	<code><img width="50" src="https://user-images.githubusercontent.com/25181517/189715289-df3ee512-6eca-463f-a0f4-c10d94a06b2f.png" alt="Figma" title="Figma"/></code>
</div>

![image](https://github.com/U-is-Ni-in-Korea/Server-Unitied-Kotlin/assets/45380072/85eb6c7a-287b-45fa-b80d-9885e1907df9)
