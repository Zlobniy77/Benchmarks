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
 * Created by Aleksandr on 16.10.2016.
 */
//@State(Scope.Thread)
@State( Scope.Benchmark )
public class TestBench2 {

    volatile double x = Math.PI;

    private Map<String, String> map;
    private List<Integer> milion;

    int v = 1;
    volatile int b = 1;

    @Setup
    public void setup() throws Exception{
        map = new HashMap<>();
        for( int i = 0; i < 1000; i++ ){
            map.put( "i", "" + i );
        }

        milion = Stream.generate(new Random()::nextInt)
                .limit(1000000)
                .map( Integer::valueOf )
                .collect( Collectors.toList() );


    }

    @Benchmark
    @BenchmarkMode( Mode.AverageTime )
    @OutputTimeUnit( TimeUnit.NANOSECONDS )
    public int baseTest() throws InterruptedException{
        return 1;
        //System.out.println( value );
    }


    @Benchmark
//    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime})
    @BenchmarkMode( Mode.AverageTime )
    @OutputTimeUnit( TimeUnit.NANOSECONDS )
    public int plainTest() throws InterruptedException{
        return v++;
        //System.out.println( value );
    }


    @Benchmark
    @BenchmarkMode( Mode.AverageTime )
    @OutputTimeUnit( TimeUnit.NANOSECONDS )
    public int volatileTest() throws InterruptedException{
        return b++;
        //System.out.println( value );
    }

    @Benchmark
    @BenchmarkMode( Mode.AverageTime )
    @OutputTimeUnit( TimeUnit.NANOSECONDS )
    public void emptyTest() throws InterruptedException{
        int a = 1;
        //System.out.println( value );
    }


    @Benchmark
    @BenchmarkMode( Mode.AverageTime )
    @OutputTimeUnit( TimeUnit.NANOSECONDS )
    public void measureAvgTime() throws InterruptedException{
        long value = map.entrySet().stream()
                .count();
        //System.out.println( value );
    }

    @Benchmark
    @BenchmarkMode( Mode.AverageTime )
    @OutputTimeUnit( TimeUnit.NANOSECONDS )
    public void test2() throws InterruptedException{
        map.entrySet().stream()
                .map( v -> Integer.valueOf( v.getValue() ) )
                .collect( Collectors.toList() ).size();
        //System.out.println( value );
    }

    @Benchmark
    @BenchmarkMode( Mode.AverageTime )
    @OutputTimeUnit( TimeUnit.SECONDS )
    public void test3() throws InterruptedException{
        milion.stream()
                .map( v -> Integer.valueOf( v ) )
                .collect( Collectors.toList() ).size();
        //System.out.println( value );
    }

    @Benchmark
    @BenchmarkMode( Mode.AverageTime )
    @OutputTimeUnit( TimeUnit.NANOSECONDS )
    public void measureShared( TestBench2 state ){
        // All benchmark threads will call in this method.
        //
        // Since BenchmarkState is the Scope.Benchmark, all threads
        // will share the state instance, and we will end up measuring
        // shared case.
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
