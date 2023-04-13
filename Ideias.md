## Ideias

- Acelerometro/Giroscopio (obrigatório colocar no capacete da maneira discutida com os grupos, outro posicionamento não será possivel)
https://mauser.pt/catalog/product_info.php?cPath=1667_2669_2670&products_id=096-6710 

      - Consumo de energia: 0,9 mA no modo normal e 1,25 mA no modo combo de alto desempenho até 1,6 kHz
      - Dimensões: 2 x 2cm	 		

- sensor bpm (posicionamento no capacete decidido com os grupos) | contacto com pele obrigatório
https://www.amazon.es/TECNOIOT-Pulsesensor-Heart-Sensor-Module/dp/B07RD2LLK6	

       - Operating Voltage: +5V or +3.3V
       - Current Consumption: 4mA
       - Diametro: 16MM
       
- GPS	
https://www.amazon.es/ICQUANZX-GY-NEO6MV2-NEO-6M-cer%C3%A1mica-superpotente/dp/B088LR3488

        - Supply voltage: 3.6V
        - Maximum DC current at any output: 10mA
        - Dimensões modulo: 35 x 25mm
        - Dimensões antena: 25 x 25mm

- indicador bateria	
https://www.amazon.es/WOWOWO-indicador-Pantalla-Capacidad-Potencia/dp/B091T33W9T

        - Working current ~5mA
        - Dimensões extriores: 45 x 20mm

- bms
https://www.ptrobotics.com/alimentacao/10495-modulo-para-carregamento-de-baterias-com-protecao-de-carga-tp5100-1s-2s-42v-84v.html		

        - Dimensões: 25 x 17 x 5mm

- bateria
https://www.ptrobotics.com/baterias-lipo/3231-polymer-lithium-ion-battery-37v-850mah.html	

         - 3.7V
         - 850mah
         - Dimensões:
                        Length: 48 ± 0.4mm
                        Width: 30 ± 0.4mm
                        Thickness: 6.0 0.2mm
                        Cable: 150 ± 3.0mm (26AWG UL 1007)

- Módulo de conversor de impulso automático DC-DC (X2)https://pt.aliexpress.com/item/1005002849970161.html?algo_exp_id=c0f3ad17-9407-4700-9637-410c1c530cdd-27&pdp_ext_f=%7B%22sku_id%22%3A%2212000022459998327%22%7D&pdp_npi=3%40dis%21EUR%210.61%210.54%21%21%21%21%21%40211bf55216790429174211337d06c7%2112000022459998327%21sea%21PT%212445244118&curPageLogUid=ilFDCSWB2MDd

- botão de ligar led frontal (mostrado aos grupos presencialmente)

- buzzer

         - current <25MA

- leds indicadores (piscas e luz frontal)

         - Diametro max: 10mm

- ESP32 dev kit

         - Dimensões:55 x 27 x 4.8mm (aproximadamente)

- breadboard (protótipo será desenvolvido sobre breadboard) (versão sem breadboard a combinar com os grupos de design)
- (Já combinado com os grupos de design. A versão a implementar no capacete apenas utilizará o esp32(controlador) apoiado com material proprio do capacete desenvolvido pelos grupos, não será utilizada a breadboard).

         - Dimensões: 54 x 82 x 9mm

-NOTAS
      As dimensões finais dos modulos/sensores será ligeiramente mais pequena por possibilidade de soldar os cabos diretamente aos sensore, evitando a utilização dos pins, como foi discutido com os grupos.
      Ter em consideração as informações passadas sobre limitações de distância dos sensores ao controlador (esp32).
      As alterações discutidas em especifico com cada grupo não estarão aqui indicadas mas refletem-se no respetivo grupo.
      


-------

Comunicação

O capacete comunicará com uma app android que por sua vez comunicará com um web server e base dados.
Detalhos especificados nos documentos do projeto.


![comunicacao](https://user-images.githubusercontent.com/75837129/225879553-e34f789d-0b10-48fb-9bc0-35992ba3a628.jpeg)




