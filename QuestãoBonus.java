import java.util.*;
import java.util.stream.Collectors;

public class main {

    static class Result {
        final String algoritmo;
        final int frames;
        final List<Integer> refs;
        final int totalRefs, faltas;
        final double taxaFaltas;
        final List<int[]> framesGrid;
        final List<Boolean> hits;
        final List<String> eventos;
        Result(String algoritmo, int frames, List<Integer> refs,
               int faltas, List<int[]> framesGrid, List<Boolean> hits, List<String> eventos) {
            this.algoritmo = algoritmo;
            this.frames = frames;
            this.refs = refs;
            this.totalRefs = refs.size();
            this.faltas = faltas;
            this.taxaFaltas = totalRefs == 0 ? 0.0 : (faltas * 100.0 / totalRefs);
            this.framesGrid = framesGrid;
            this.hits = hits;
            this.eventos = eventos;
        }
    }

    interface PageReplace { Result simulate(int frames, List<Integer> refs); }

    static class LRUWithTimestamps implements PageReplace {
        @Override
        public Result simulate(int frames, List<Integer> refs) {
            Set<Integer> memoria = new HashSet<>();
            Map<Integer, Long> tempoUso = new HashMap<>();
            long tempo = 0L;
            int faltas = 0;
            List<int[]> framesGrid = new ArrayList<>();
            List<Boolean> hits = new ArrayList<>();
            List<String> eventos = new ArrayList<>();

            for (int ref : refs) {
                tempo++;
                boolean hit = memoria.contains(ref);
                StringBuilder ev = new StringBuilder();
                if (hit) {
                    tempoUso.put(ref, tempo);
                    ev.append("HIT: ").append(ref).append(" já estava na RAM.");
                } else {
                    faltas++;
                    Integer vitima = null;
                    if (memoria.size() == frames) {
                        long menor = Long.MAX_VALUE;
                        Integer lru = null;
                        for (Integer p : memoria) {
                            long t = tempoUso.getOrDefault(p, Long.MIN_VALUE);
                            if (t < menor) { menor = t; lru = p; }
                        }
                        if (lru != null) { memoria.remove(lru); tempoUso.remove(lru); vitima = lru; }
                    }
                    memoria.add(ref);
                    tempoUso.put(ref, tempo);
                    if (vitima == null) ev.append("FALTA: ").append(ref).append(" carregada.");
                    else ev.append("FALTA: expulsou ").append(vitima).append(" e carregou ").append(ref).append(".");
                }
                hits.add(hit);
                eventos.add(ev.toString());
                framesGrid.add(snapshotLRU(memoria, tempoUso, frames));
            }
            return new Result("LRU (timestamps)", frames, refs, faltas, framesGrid, hits, eventos);
        }
        private int[] snapshotLRU(Set<Integer> memoria, Map<Integer, Long> tempoUso, int frames) {
            int[] arr = new int[frames]; Arrays.fill(arr, -1);
            List<Integer> ord = new ArrayList<>(memoria);
            ord.sort(Comparator.comparingLong(p -> tempoUso.getOrDefault(p, Long.MIN_VALUE)));
            for (int i = 0; i < ord.size() && i < frames; i++) arr[i] = ord.get(i);
            return arr;
        }
    }

    static class FIFO implements PageReplace {
        @Override
        public Result simulate(int frames, List<Integer> refs) {
            Deque<Integer> fila = new ArrayDeque<>();
            Set<Integer> emMemoria = new HashSet<>();
            int faltas = 0;
            List<int[]> framesGrid = new ArrayList<>();
            List<Boolean> hits = new ArrayList<>();
            List<String> eventos = new ArrayList<>();

            for (int ref : refs) {
                boolean hit = emMemoria.contains(ref);
                StringBuilder ev = new StringBuilder();
                if (hit) {
                    ev.append("HIT: ").append(ref).append(" já estava na RAM.");
                } else {
                    faltas++;
                    Integer vitima = null;
                    if (fila.size() == frames) { vitima = fila.removeFirst(); emMemoria.remove(vitima); }
                    fila.addLast(ref); emMemoria.add(ref);
                    if (vitima == null) ev.append("FALTA: ").append(ref).append(" carregada.");
                    else ev.append("FALTA: expulsou ").append(vitima).append(" e carregou ").append(ref).append(".");
                }
                hits.add(hit);
                eventos.add(ev.toString());
                framesGrid.add(snapshotFIFO(fila, frames));
            }
            return new Result("FIFO", frames, refs, faltas, framesGrid, hits, eventos);
        }
        private int[] snapshotFIFO(Deque<Integer> fila, int frames) {
            int[] arr = new int[frames]; Arrays.fill(arr, -1);
            int i = 0; for (Integer p : fila) { if (i >= frames) break; arr[i++] = p; } return arr;
        }
    }

    static void renderNarrativa(Result r) {
        System.out.println("\n--- " + r.algoritmo + " | Passo-a-passo ---");
        for (int i = 0; i < r.totalRefs; i++) {
            int ref = r.refs.get(i);
            String evento = r.eventos.get(i);
            System.out.printf("Passo %02d | Ref: %4d | %s%n", (i + 1), ref, evento);
        }
        System.out.printf("Total de faltas: %d | Taxa: %.2f%%%n", r.faltas, r.taxaFaltas);
    }

    static void renderGrid(Result r, String subtitulo) {
        System.out.println("\n--- " + r.algoritmo + " | " + subtitulo + " ---");
        System.out.print("Ref : "); for (int ref : r.refs) System.out.printf("%4d", ref); System.out.println();
        for (int f = 0; f < r.frames; f++) {
            System.out.printf("F%02d : ", f);
            for (int c = 0; c < r.totalRefs; c++) {
                int val = r.framesGrid.get(c)[f];
                System.out.printf("%4s", val == -1 ? "-" : Integer.toString(val));
            }
            System.out.println();
        }
        System.out.print("Evt : "); for (int c = 0; c < r.totalRefs; c++) {
            boolean hit = r.hits.get(c); System.out.printf("%4s", hit ? "H" : "F"); }
        System.out.println();
    }

    static void comparar(Result lru, Result fifo) {
        System.out.println("\n=== Comparação (LRU x FIFO) ===");
        System.out.printf("LRU  → Faltas: %d | Taxa: %.2f%%%n", lru.faltas, lru.taxaFaltas);
        System.out.printf("FIFO → Faltas: %d | Taxa: %.2f%%%n", fifo.faltas, fifo.taxaFaltas);
        if (lru.faltas < fifo.faltas) System.out.println("LRU teve menos faltas.");
        else if (lru.faltas > fifo.faltas) System.out.println("FIFO teve menos faltas.");
        else System.out.println("Empate.");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Simulação de Substituição de Páginas (LRU vs FIFO) ===");
        System.out.print("Número de frames: ");
        int frames = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Sequência de referências (ex: 7,0,1,2,0,3,0,4,2,3,0,3,2): ");
        String linha = sc.nextLine().trim();
        if (linha.isEmpty()) linha = "7,0,1,2,0,3,0,4,2,3,0,3,2";

        List<Integer> refs = Arrays.stream(linha.split("[,\\s]+"))
                                   .filter(s -> !s.isEmpty())
                                   .map(Integer::parseInt)
                                   .collect(Collectors.toList());

        PageReplace lru = new LRUWithTimestamps();
        PageReplace fifo = new FIFO();

        Result rLRU  = lru.simulate(frames, refs);
        Result rFIFO = fifo.simulate(frames, refs);

        renderNarrativa(rLRU);
        renderGrid(rLRU, "Grelha (linhas = frames; topo = menos recente)");

        renderNarrativa(rFIFO);
        renderGrid(rFIFO, "Grelha (linhas = frames; topo = mais antigo)");

        comparar(rLRU, rFIFO);
    }
}
