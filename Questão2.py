particoes = [
    {"id": 1, "tamanho": 100, "livre": True, "processo": None},
    {"id": 2, "tamanho": 150, "livre": True, "processo": None},
    {"id": 3, "tamanho": 200, "livre": True, "processo": None},
    {"id": 4, "tamanho": 250, "livre": True, "processo": None},
    {"id": 5, "tamanho": 300, "livre": True, "processo": None},
]


def exibir_memoria():
    print("\n=== ESTADO ATUAL DA MEMÓRIA ===")
    for p in particoes:
        if p["livre"]:
            print(f"Partição {p['id']}: {p['tamanho']} unidades - LIVRE")
        else:
            proc = p["processo"]
            frag = p["tamanho"] - proc["tamanho"]
            print(f"Partição {p['id']}: {p['tamanho']} unidades - "
                  f"Ocupada por {proc['nome']} ({proc['tamanho']}u) "
                  f"| Fragmentação interna: {frag}u")
    print("================================\n")


def alocar_processo(nome, tamanho):
    for p in particoes:
        if p["livre"] and p["tamanho"] >= tamanho:
            p["livre"] = False
            p["processo"] = {"nome": nome, "tamanho": tamanho}
            frag = p["tamanho"] - tamanho
            print(f"Processo {nome} ({tamanho}u) alocado na partição {p['id']} ({p['tamanho']}u).")
            print(f"   → Fragmentação interna: {frag} unidades.")
            return
    print(f"Falha ao alocar {nome} ({tamanho}u): nenhuma partição livre adequada encontrada.")


def liberar_processo(nome):
    for p in particoes:
        if not p["livre"] and p["processo"]["nome"] == nome:
            print(f"Liberando partição {p['id']} ocupada por {nome}.")
            p["livre"] = True
            p["processo"] = None
            return
    print(f"Processo {nome} não encontrado na memória.")


def fragmentacao_total():
    total = 0
    for p in particoes:
        if not p["livre"]:
            total += p["tamanho"] - p["processo"]["tamanho"]
    print(f"Fragmentação interna total: {total} unidades.\n")


def main():
    exibir_memoria()

    alocar_processo("P1", 90)
    alocar_processo("P2", 140)
    alocar_processo("P3", 180)
    liberar_processo("P2")
    alocar_processo("P4", 100)
    alocar_processo("P5", 350)

    exibir_memoria()
    fragmentacao_total()


if __name__ == "__main__":
    main()
