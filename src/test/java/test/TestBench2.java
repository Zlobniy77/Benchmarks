package test;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Zlobniy on 16.10.2016.
 */
//@State(Scope.Thread)
@State( Scope.Benchmark )
public class TestBench2 {

    volatile double x = Math.PI;

    private Map<String, String> map;
    private List<Integer> million;

    int v = 1;
    volatile int b = 1;

    @Setup
    public void setup() throws Exception{
        map = new HashMap<>();
        for( int i = 0; i < 1000; i++ ){
            map.put( "i", "" + i );
        }

        million = Stream.generate(new Random()::nextInt)
                .limit(1000000)
                .map( Integer::valueOf )
                .collect( Collectors.toList() );


    }

    @Benchmark
    @BenchmarkMode( Mode.AverageTime )
    @OutputTimeUnit( TimeUnit.NANOSECONDS )
    public int baseTest() throws InterruptedException{
        return 1;
    }


    @Benchmark
//    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime})
    @BenchmarkMode( Mode.AverageTime )
    @OutputTimeUnit( TimeUnit.NANOSECONDS )
    public int plainTest() throws InterruptedException{
        return v++;
    }


    @Benchmark
    @BenchmarkMode( Mode.AverageTime )
    @OutputTimeUnit( TimeUnit.NANOSECONDS )
    public int volatileTest() throws InterruptedException{
        return b++;
    }

    @Benchmark
    @BenchmarkMode( Mode.AverageTime )
    @OutputTimeUnit( TimeUnit.NANOSECONDS )
    public void emptyTest() throws InterruptedException{
        int a = 1;
    }


    @Benchmark
    @BenchmarkMode( Mode.AverageTime )
    @OutputTimeUnit( TimeUnit.NANOSECONDS )
    public void streamMapCount() throws InterruptedException{
        map.entrySet().stream().count();
    }

    @Benchmark
    @BenchmarkMode( Mode.AverageTime )
    @OutputTimeUnit( TimeUnit.NANOSECONDS )
    public void streamMapToMap() throws InterruptedException{
        map.entrySet().stream()
                .map( v -> Integer.valueOf( v.getValue() ) )
                .collect( Collectors.toList() ).size();
    }

    @Benchmark
    @BenchmarkMode( Mode.AverageTime )
    @OutputTimeUnit( TimeUnit.SECONDS )
    public void streamMap() throws InterruptedException{
        million.stream()
                .map( v -> Integer.valueOf( v ) )
                .collect( Collectors.toList() ).size();
    }

    @Benchmark
    @BenchmarkMode( Mode.AverageTime )
    @OutputTimeUnit( TimeUnit.NANOSECONDS )
    public void measureShared( TestBench2 state ){
        state.x++;
    }

    public static void main( String[] args ) throws RunnerException{

        Options opt = new OptionsBuilder()
                .include( test.TestBench2.class.getSimpleName() )
                .forks( 1 )
                .warmupIterations( 5 )
                .measurementIterations( 5 )
                .threads( 4 )
                .build();

        new Runner( opt ).run();
    }

}
