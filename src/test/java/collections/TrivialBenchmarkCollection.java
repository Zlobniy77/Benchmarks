package collections;

import common.SetupBenchmarks;
import entity.Person;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Zlobniy on 16.10.2016.
 */
//@State(Scope.Thread)
@State( Scope.Benchmark )
public class TrivialBenchmarkCollection extends SetupBenchmarks {

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
    public void oldObjectToMap() throws InterruptedException{
        final List<String> names = new ArrayList<>( someData.size() );
        for( Person s : someData ){
            names.add( s.getName() + "_" + s.getSurName() );
        }
    }

    @Benchmark
    @BenchmarkMode( Mode.AverageTime )
    @OutputTimeUnit( TimeUnit.NANOSECONDS )
    public void measureShared( TrivialBenchmarkCollection state ){
        state.x++;
    }

    public static void main( String[] args ) throws RunnerException{

        Options opt = new OptionsBuilder()
                .include( TrivialBenchmarkCollection.class.getSimpleName() )
                .forks( 1 )
                .warmupIterations( 5 )
                .measurementIterations( 5 )
                .threads( 1 )
                .build();

        new Runner( opt ).run();
    }

}
