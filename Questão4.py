import gc
import sys

# Classe que simula uso de memória
class Objeto:
    def __init__(self, nome):
        self.nome = nome
        self.dados = [0] * 10**6  # simulando que ocupa bastante memória
        print(f"Objeto '{self.nome}' criado.")

    def __del__(self):
        print(f"Objeto '{self.nome}' destruído.")

# Cenário 1: destruição automática
# Aqui o objeto é destruído automaticamente quando a referência some
print("\n CENÁRIO 1: Destruição automática")
obj1 = Objeto("A")
print(f"Contagem de referência de obj1: {sys.getrefcount(obj1)}")
obj1 = None  # removi a referência, o objeto é destruído automaticamente

# Cenário 2: referência circular
# Agora crio dois objetos que se referenciam, isso impede a destruição automática
print("\nCENÁRIO 2: Referência circular")
obj2 = Objeto("B")
obj3 = Objeto("C")
obj2.outro = obj3
obj3.outro = obj2
del obj2, obj3  # mesmo apagando, eles ainda se referenciam
print("Forçando coleta de lixo com gc.collect()...")
gc.collect()  # aqui o coletor de lixo entra em ação e destrói os dois

# Cenário 3: auto-referência
# Um objeto que se referencia sozinho
print("\nCENÁRIO 3: Auto-referência")
obj4 = Objeto("D")
obj4.eu = obj4
del obj4
gc.collect()

# Estatísticas do coletor
# Aqui mostra as informações das três gerações do coletor de lixo
print("\nESTATÍSTICAS DO COLETOR DE LIXO")
stats = gc.get_stats()
for i, geracao in enumerate(stats):
    print(f"Geração {i}: {geracao}")
