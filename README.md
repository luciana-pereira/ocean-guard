<a href="" target="_blank">Potuguês **🇧🇷**</a> | <a href="./README_en.md" target="_blank">English **🇺🇸**</a>

## Ocean Guard

<div align="center">
    <img src="https://github.com/luciana-pereira/ocean-guard/assets/37550557/b1f94ae5-4215-45c7-8b28-fb49a6815fd8" alt="logo" width="250" height="250">
</div>

## Introdução
Ocean Guard é um aplicativo Android desenvolvido para auxiliar na preservação dos oceanos. O aplicativo permite a identificação de atividades de pesca ilegal, avistamento de espécies em perigo e empoderamento das comunidades costeiras. 
Utiliza APIs gratuitas e serviços da AWS para fornecer dados em tempo real e promover a conscientização ambiental. 

## Aplicação Mobile

<div align="center">
    <img src="https://github.com/luciana-pereira/ocean-guard/assets/37550557/23e73cde-5832-4426-b4c4-4990802fd7f2" alt="Login" width=200" height="300">
    <img src="https://github.com/luciana-pereira/ocean-guard/assets/37550557/db1d8d9e-3fba-49f5-9907-5d867482033e" alt="Cadastro" width="200" height="300">
</div>

## Funcionalidades
- **Autenticação de Usuário:** Login seguro através da integração com o [Firebase]() como banco de dados.
- **Monitoramento de Pesca Ilegal:** Exibe dados sobre atividades de pesca ilegal.
- **Relatórios de Avistamentos:** Permite aos usuários reportar avistamentos de espécies em perigo.
- **Informações das Comunidades Costeiras:** Dados sobre as condições das comunidades costeiras e como ajudar.

## Tecnologias Utilizadas
- **Linguagem de Programação:** Kotlin
- **Plataforma:** Android
- **APIs Utilizadas:** 
  - [Firebase]().
  - [API Global Fishing Watch]() da [Global Fishing Watch](https://globalfishingwatch.org/) para monitorar a atividade de pesca.
  - [API da Ocean Biogeographic Information System - OBIS](https://obis.org/), [Swagger OBIS](https://api.obis.org/) e [documentação](https://manual.obis.org/access) para obter dados sobre espécies marinhas, para identificar ou ajudar na identificação/avistamento de espécies em perigo.
- **Serviços da AWS:** Utilizados para armazenamento e processamento de dados, como:
  - [AWS Cognito]() para autenticação, configuramos um pool de usuários no AWS Cognito.
  - [S3]() para armazenar dados e imagens, criamos um bucket S3 para armazenar imagens e dados de avistamentos.
- **Mapbox** 
- **Bibliotecas:** [Retrofit](), [Moshi](https://github.com/square/moshi)

## Estrutura do Projeto
```
OceanGuard/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/greenconect/oceanguard/
│   │   │   │   ├── model/
│   │   │   │   │   ├── AuthRequest.kt
│   │   │   │   │   ├── AuthResponse.kt
│   │   │   │   │   ├── IllegalFishingData.kt
│   │   │   │   │   └── User.kt
│   │   │   │   ├── network/
│   │   │   │   │   ├── RetrofitClient.kt
│   │   │   │   │   ├── SupabaseApi.kt
│   │   │   │   │   ├── FishingApi.kt
│   │   │   │   │   ├── SupabaseRetrofitClient.kt
│   │   │   │   │   └── FishingRetrofitClient.kt
│   │   │   │   ├── repository/
│   │   │   │   │   ├── UserRepository.kt
│   │   │   │   │   └── FishingRepository.kt
│   │   │   │   ├── ui/
│   │   │   │   │   ├── MainActivity.kt
│   │   │   │   │   └── LoginActivity.kt
│   │   │   │   └── util/
│   │   │   │       └── Extensions.kt
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   └── activity_login.xml
│   │   │   │   └── values/
│   │   │   │       ├── strings.xml
│   │   │   │       └── styles.xml
├── README.md
└── build.gradle
```

## Instalação
Clone o Repositório

```bash
$> git clone https://github.com/luciana-pereira/ocean-guard.git
$> cd ocean-guard
```

## Adicione as Chaves de API e URLs
Para rodar a aplicação e necessario incluir as chaves da API.

Ex:
```
const val FISHING_API_URL = "https://api.fishingdata.com"
```

## Desenvolvedores
:octocat:
Esta aplicação foi desenvolvida por:

<table align="center">
  <tr>
    <td align="center">
      <a href="https://github.com/flavialbraz" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/78583429?v=4" width="100px;" alt="Foto de Flavia no GitHub"/><br>
        <sub>
          <b>Flavia </b>
        </sub>
      </a>
    </td>
     <td align="center">
      <a href="https://github.com/luciana-pereira" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/37550557?v=4" width="100px;" alt="Foto de Fernanda no GitHub"/><br>
        <sub>
          <b>Luciana Pereira</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/matheus-poro" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/111644802?v=4" width="100px;" alt="Foto de Matheus Cavalcante no GitHub"/><br>
        <sub>
          <b>Matheus Cavalcante</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/TobiasGustavo" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/88210620?v=4" width="100px;" alt="Foto de Tobias Soares no GitHub"/><br>
        <sub>
          <b>Tobias Soares</b>
        </sub>
      </a>
    </td>
  </tr>
</table>
