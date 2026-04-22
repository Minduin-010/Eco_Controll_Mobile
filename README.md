# Eco_Controll_Mobile

App mobile para o projeto de TCC, focado no gerenciamento de cisternas e monitoramento de energia solar.

## Sobre o Projeto

O EcoControll é um sistema que permite:

- Monitoramento do nível da cisterna
- Visualização de dados em tempo real
- Histórico de leituras
- Gerenciamento de energia solar
- Login de usuários

O app se conecta com um backend feito em Ktor.

## Como Executar

1. Clonar o repositório:
   
```
git clone https://github.com/ECO-CONTROLL/Eco_Control_Front.git  
cd Eco_Control_Front
```

2. Abrir no Android Studio:

- File → Open
- Selecionar a pasta do projeto
- Aguardar o Gradle sincronizar

3. Configurar o backend:

No arquivo RetrofitClient.kt, alterar a BASE_URL:

```
private const val BASE_URL = "http://192.168.15.10:8080/"
```

O celular precisa estar na mesma rede do computador.

4. Rodar o app:

Pelo terminal, com o celular conectado por cabo com depuração usb:

```
./gradlew installDebug
```

## Login

Login normal (usa backend):

usuario: admin  
senha: cisterna123  

- autentica no backend
- salva token
- permite auto-login

Login de desenvolvimento (frontend):

usuario: front  
senha: front123  

- não usa backend
- não salva token
- não faz auto-login

## Comunicação com o Backend

POST /api/auth/login  

## Teste Local

Para funcionar:

- backend rodando
- celular na mesma rede
- IP correto configurado

## Estrutura

app/src/main/java/com/example/eco_controll_mobile/

- ui/features/auth
- ui/features/dashboard
- ui/features/profile
- ui/features/registration
- ui/features/reports
- ui/features/resources


## Tecnologias

- Kotlin
- Jetpack Compose
- Retrofit
- Coroutines

## Equipe

Icaro 
