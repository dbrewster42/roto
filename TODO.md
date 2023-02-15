1. calculate estimated win totals based on roto points
2. save stats in database for comparison. could do change in last month
3. assign values more specifically. aka do average of middle 8 teams




#### hitting categories
    @Column(name="Name")
    private String name;
    @Column(name="Runs")
    private int run;
    @Column(name="HR")
    private int homeRun;
    @Column(name="RBI")
    private int rbi;
    @Column(name="SB")
    private int sb;
    @Column(name="AVG")
    private double avg;
    @Column(name="OPS")
    private double ops;