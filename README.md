# Citações Diárias - Android Nativo
Um aplicativo **Android Nativo** simples e elegante para visualização de frases inspiradoras. O projeto foi construído utilizando *Kotlin* para demonstrar o domínio de componentes básicos do SDK Android.
## Funcionalidades

- Exibição de frases motivacionais de forma aleatória.
- Interface intuitiva com botão de ação para nova citação.
- Layout responsivo para diferentes tamanhos de tela Android.
- Gerenciamento de recursos de string de forma organizada.

## Desenvolvimento
- [x] Criação do layout da Activity em XML
- [x] Lógica de sorteio de frases em Kotlin
- [x] Configuração de cores e temas (Themes.xml)
- [x] Vínculo de componentes (Button/TextView)
- [ ] Implementação de função para compartilhar frase
## Tecnologias Utilizadas
| Tecnologia | Uso |
| --- | --- |
| Kotlin | Linguagem de programação principal |
| Android SDK | Ferramentas de desenvolvimento nativo |
| XML | Definição de interface e layouts |
> [!TIP]
> As frases são armazenadas em um Array local dentro da lógica do Kotlin, permitindo que o app funcione totalmente offline.
## Como rodar o projeto
Abra o projeto no **Android Studio** e execute no seu emulador ou dispositivo físico:
`./gradlew installDebug`
```kotlin

// Exemplo de como a lógica de sorteio funciona
fun novaFrase() {
val numeroAleatorio = Random().nextInt(frases.size)
textoResultado.text = frases[numeroAleatorio]
}

```
