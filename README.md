# Trabalho de Gerenciamento de Memórias

Repositório referente ao **trabalho da disciplina de Sistemas Operacionais**, com foco no **gerenciamento de memórias** utilizando as linguagens **C**, **Java** e **Python**.

---

## Integrantes do Grupo

| Nome Completo | RA / Matrícula | Curso | 
|----------------|----------------|--------|
| Gustavo Mascarenhas Sfier Arando | 24047600-2 | Engenharia de Software | 
| Thiago Felipe Telma  | 24099834-2 | Engenharia de Software | 
| Thiago Torá Frazão Martins  | 24200357-2 | Engenharia de Software | 
| Tamires de Sousa Martins | 24489346-2 | Engenharia de Software | 
| Felipe Roberto Domingos Leite | 24000580-2 | Engenharia de Software | 
| Jackeline Paola Martins | 24195840-2 | Engenharia de Software | 
| Matheus de Souza Calistro | 2314703-5 | Engenharia de Software | 

---

##  Professor Orientador
**JOSE CARLOS DOMINGUES FLORES**  
📚 Disciplina: *Sistemas Operacionais*

---

## Instituição
**Unicesumar – Centro Universitário Cesumar**

**Semestre:** 4º semestre de 2025

---

## 💡 Descrição do Projeto

O projeto tem como objetivo demonstrar o funcionamento e comparação de técnicas de gerenciamento de memória em diferentes linguagens de programação. São analisados conceitos como:

### 1. Alocação Estática vs. Dinâmica (Linguagem C)

#### Alocação Estática:
Acontece em tempo de compilação, ou seja, antes do programa começar a rodar. O tamanho e o tipo das variáveis são definidos fixamente no código.

* **Vantagens:** Maior velocidade de execução e menor risco de vazamento de memória (o sistema libera automaticamente).
* **Desvantagens:** Pouca flexibilidade e desperdício de memória.

#### Alocação Dinâmica:
Ocorre em tempo de execução, enquanto o programa está rodando. O programador usa funções como `malloc()`, `calloc()`, `realloc()` e `free()`.

* **Vantagens:** Alta flexibilidade e uso eficiente da memória.
* **Desvantagens:** Mais complexa de gerenciar (o programador deve liberar a memória manualmente) e mais lenta (envolve operações em tempo de execução).

### 2. Simulação de Fragmentação de Memória (Linguagem Python)

* **Fragmentação Interna:** Acontece quando um bloco de memória alocado é maior do que o necessário, deixando espaço desperdiçado dentro do próprio bloco. Isso ocorre geralmente em alocação estática ou com blocos de tamanho fixo.
* **Fragmentação Externa:** Acontece quando há vários blocos de memória livres espalhados, mas nenhum é grande o suficiente para atender uma nova requisição, mesmo que haja memória total suficiente. Isso ocorre em alocação dinâmica.

### 3. Algoritmo de Substituição de Página - FIFO (Linguagem Java)

Funciona com o princípio do "**primeiro que entra, primeiro que sai**".

A página mais antiga (a que entrou primeiro) é substituída pela nova quando a memória (ou o conjunto de quadros) está cheia.

* **Vantagens:** Simples de implementar e baixo custo de processamento.
* **Desvantagens:** Não considera o uso recente e pode causar a **Anomalia de Belady**.

### 4. Garbage Collection (Coleta de Lixo) em Python

O Python utiliza principalmente a **Contagem de Referências**, liberando um objeto quando seu contador chega a zero.

* **Problema:** A contagem de referências não consegue detectar **ciclos de referência**.
* **Solução:** O Python usa o **Coletor de Lixo Geracional** para complementar a contagem de referências. Este coletor detecta objetos em ciclos de referência e organiza os objetos em gerações, fazendo coletas mais frequentes nas gerações mais novas.

### 5. Comparação de Desempenho de Alocação (Linguagem Python)

* **Pilha (Stack):** É uma área de memória organizada no formato LIFO. A alocação e liberação são muito **rápidas e previsíveis** (basta mover o *stack pointer*).
* **Heap:** É uma área de memória usada para alocação dinâmica. O sistema precisa procurar um bloco livre e gerenciar fragmentações, o que demanda **mais tempo e processamento**.

### Questão Bônus: Implementação do Algoritmo LRU (Linguagem Java)

Simulação do algoritmo *Least Recently Used* (LRU), que substitui a página que foi usada há mais tempo, e sua comparação de desempenho com o FIFO.

---
