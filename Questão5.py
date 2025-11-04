import time
import statistics

# ==========================================
# COMPARAÇÃO DE TEMPO DE ALOCAÇÃO DE MEMÓRIA
# ==========================================

# Configurações do experimento
N = 1_000_000          # número de objetos/variáveis
REPETICOES = 5          # quantas vezes repetir cada teste para tirar média

# ---------------------------------------------------------
# 1. TESTE DE ALOCAÇÃO NA "PILHA"
# Em Python, tudo é objeto, mas podemos simular alocação local
# criando e destruindo variáveis dentro de uma função.
# ---------------------------------------------------------

def alocacao_pilha():
    for _ in range(N):
        x = 123  # variável local (teoricamente, na pilha)
        # após sair do escopo, x é "destruída" automaticamente

# ---------------------------------------------------------
# 2. TESTE DE ALOCAÇÃO NO "HEAP"
# Aqui simulamos alocação dinâmica criando objetos com `list()` ou `object()`
# e desalocando-os explicitamente com `del`.
# ---------------------------------------------------------

def alocacao_heap():
    objs = []
    for _ in range(N):
        objs.append(object())  # cria objeto no heap
    del objs  # libera memória (coleta de lixo fará o restante)

# ---------------------------------------------------------
# 3. MEDIÇÃO DE TEMPOS
# ---------------------------------------------------------

def medir_tempo(func):
    """Executa a função e retorna o tempo gasto em segundos."""
    inicio = time.time()
    func()
    fim = time.time()
    return fim - inicio

tempos_pilha = []
tempos_heap = []

print("Executando testes...\n")

for i in range(REPETICOES):
    t_pilha = medir_tempo(alocacao_pilha)
    t_heap = medir_tempo(alocacao_heap)
    tempos_pilha.append(t_pilha)
    tempos_heap.append(t_heap)
    print(f"Rodada {i+1}: pilha = {t_pilha:.4f}s | heap = {t_heap:.4f}s")

# ---------------------------------------------------------
# 4. RESULTADOS
# ---------------------------------------------------------

media_pilha = statistics.mean(tempos_pilha)
media_heap = statistics.mean(tempos_heap)

diferenca_percentual = ((media_heap - media_pilha) / media_pilha) * 100

print("\n===== RESULTADOS MÉDIOS =====")
print(f"Tempo médio (pilha): {media_pilha:.4f} segundos")
print(f"Tempo médio (heap) : {media_heap:.4f} segundos")
print(f"Diferença percentual: {diferenca_percentual:.2f}%")

# ---------------------------------------------------------
# 5. EXPLICAÇÃO (COMENTÁRIOS)
# ---------------------------------------------------------
# Em geral, a alocação na pilha é mais rápida porque:
# - A pilha é uma área contígua de memória com gerenciamento automático.
# - Criar e destruir variáveis locais é feito apenas ajustando o ponteiro da pilha.
#
# Já o heap envolve:
# - Chamadas ao alocador de memória do sistema (malloc/new).
# - Gerenciamento mais complexo e fragmentação.
# - Em Python, ainda há overhead do coletor de lixo (garbage collector).
#
# Por isso, espera-se que o tempo de alocação no heap seja maior.
# No entanto, devido à natureza do interpretador Python e otimizações internas,
# pode haver variação entre execuções.
# Além disso, a diferença pode ser menos pronunciada do que em linguagens compiladas, onde a distinção entre pilha e heap é mais clara.