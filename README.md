# GetHttp
App Android para consulta de endereços pelo CEP usando BrasilAPI e Material Design 3.

---

## 📱 Telas

| Campo | Descrição |
|---|---|
| CEP | Código postal (8 dígitos) |
| Logradouro | Nome da rua/avenida |
| Bairro | Bairro do endereço |
| Cidade | Município |
| Estado | UF |
| Coordenadas | Latitude e Longitude |

---

## 🚀 Funcionalidades

- Consulta de CEP em tempo real via HTTP
- Interface com **Material Design 3 (Material You)**
- Suporte a **Dynamic Color** — adapta as cores à wallpaper do usuário (Android 12+)
- Tema claro e escuro automático
- Indicador de carregamento circular
- Tratamento de erros (CEP inválido, falha de conexão)

---

## 🛠️ Tecnologias

- **Linguagem:** Kotlin
- **UI:** Material Design 3
- **HTTP:** OkHttp
- **API:** [BrasilAPI v2](https://brasilapi.com.br/docs#tag/CEP)
- **Min SDK:** 26 (Android 8.0)
- **Target SDK:** 35 (Android 15)

---

## ⚙️ Como rodar o projeto

### Pré-requisitos

- Android Studio Hedgehog ou superior
- JDK 17+
- Dispositivo ou emulador com Android 8.0+

### Passos

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/GetHttp.git

# Abra no Android Studio
File → Open → selecione a pasta do projeto

# Rode o app
Clique em ▶️ Run ou use Shift+F10
```

---

## 📦 Dependências

No `build.gradle (app)`:

```gradle
dependencies {
    // Material Design 3
    implementation "com.google.android.material:material:1.12.0"

    // OkHttp para requisições HTTP
    implementation("com.squareup.okhttp3:okhttp:5.3.2")
}
```

No `AndroidManifest.xml`, permissão de internet obrigatória:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

---

## 🌐 API utilizada

**BrasilAPI — CEP v2**

```
GET https://brasilapi.com.br/api/cep/v2/{cep}
```

Exemplo de resposta:

```json
{
  "cep": "13083852",
  "state": "SP",
  "city": "Campinas",
  "neighborhood": "Jardim Santa Genebra II (Barão Geraldo)",
  "street": "Avenida Doutor Romeu Tórtima",
  "location": {
    "type": "Point",
    "coordinates": {
      "longitude": "-47.0860843",
      "latitude": "-22.8215655"
    }
  }
}
```

---

## 📁 Estrutura do projeto

```
app/
├── build.gradle.kts                    # Dependências e configurações do módulo
└── src/
    └── main/
        ├── AndroidManifest.xml         # Permissão de internet e configuração do app
        ├── java/com/example/gethttp/
        │   ├── App.kt                  # Inicialização do Dynamic Color
        │   └── MainActivity.kt         # Lógica principal
        └── res/
            └── layout/
                └── activity_main.xml   # Layout com M3 e cores definidas diretamente
```

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
