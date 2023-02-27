public class Main {
    public static void main(String[] args) {
        System.out.println("=========Первая задача=========");
        double[][] data = DataProvider.getFirst();
        DataProvider.printData(data);
        double[] firstResult = GameTheoryProvider.getMinMax(data);
        System.out.println("  Нижняя цена игры равна " + firstResult[1]);
        System.out.println("  Верхняя цена игры равна " + firstResult[0]);
        if (firstResult[0] == firstResult[1]) {
            System.out.println("  Присутствует седловая точка на пересечении стратегий А" + firstResult[3] + " и B" + firstResult[2]);
        } else {
            System.out.println("  Седловая точка отсутствует");
        }
        System.out.println("===============================\n");

        System.out.println("======Вторая задача======");
        data = DataProvider.getSecond();
        DataProvider.printData(data);
        double[] secondPreResult = GameTheoryProvider.getMinMax(data);
        if (secondPreResult[0] == secondPreResult[1]) {
            System.out.println("Присутствует седловая точка на пересечении стратегий А" + secondPreResult[3] + " и B" + secondPreResult[2]);
            System.out.println("Дальнейшее решение не имеет смысла");
            return;
        } else {
            System.out.println("Седловая точка отсутствует\n");
        }
        data = GameTheoryProvider.checkDomination(data);
        System.out.println("-----Оптимизировано-----");
        DataProvider.printData(data);
        double[] secondResult = GameTheoryProvider.getMesh(data);
        System.out.println("Оптимальная смешанная стратегия для игрока А = (" + secondResult[0] + "; " + secondResult[1] + ")");
        System.out.println("Оптимальная смешанная стратегия для игрока B = (" + secondResult[2] + "; " + secondResult[3] + ")");
        System.out.println("Цена игры = " + secondResult[4]);
        System.out.println("===============================\n");

        System.out.println("======Третья задача======");
        data = DataProvider.getThird();
        DataProvider.printData(data);
        double[] thirdPreResult = GameTheoryProvider.getMinMax(data);
        if (thirdPreResult[0] == thirdPreResult[1]) {
            System.out.println("Присутствует седловая точка на пересечении стратегий А" + thirdPreResult[3] + " и B" + thirdPreResult[2]);
            System.out.println("Дальнейшее решение не имеет смысла");
            return;
        } else {
            System.out.println("Седловая точка отсутствует\n");
        }
        data = GameTheoryProvider.checkDomination(data);
        System.out.println("-----Оптимизировано-----");
        DataProvider.printData(data);
        int firstStrategy = 0;
        int count = 10;
        double[] thirdResult = GameTheoryProvider.getBraun(data, firstStrategy, count);
        System.out.println("Оптимальная смешанная стратегия для игрока А = (" + thirdResult[2] + "; " + thirdResult[3] + ")");
        System.out.println("Оптимальная смешанная стратегия для игрока B = (" + thirdResult[0] + "; " + thirdResult[1] + ")");
        System.out.println("Цена игры = " + thirdResult[4]);
        System.out.println("===============================\n");
    }
}
