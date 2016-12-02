package steam;

import common.SetupBenchmarks;
import entity.Order;
import entity.Product;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by Zlobniy on 02.12.2016.
 */
@State( Scope.Benchmark )
public class TrivialBenchmarkStream extends SetupBenchmarks {


    @Benchmark
    @BenchmarkMode( Mode.AverageTime )
    @OutputTimeUnit( TimeUnit.NANOSECONDS )
    public List ordersToList(  ){
        return orders
                .stream()
                .map( Order::getProducts )
                .collect( Collectors.toList() );
    }

    @Benchmark
    @BenchmarkMode( Mode.AverageTime )
    @OutputTimeUnit( TimeUnit.NANOSECONDS )
    public List<Product> ordersToListProducts(  ){
        return orders
                .stream()
                .map( Order::getProducts )
                .flatMap( Collection::stream )
                .collect( Collectors.toList() );
    }

    @Benchmark
    @BenchmarkMode( Mode.AverageTime )
    @OutputTimeUnit( TimeUnit.NANOSECONDS )
    public Object[] ordersToArray(  ){
        return orders
                .stream()
                .map( Order::getProducts )
                .map( e -> e.stream() ).toArray();

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
    @OutputTimeUnit( TimeUnit.NANOSECONDS )
    public void streamObjectToMap() throws InterruptedException{
        someData.stream()
                .parallel()
                .map( s -> s.getName() + "_" + s.getSurName() )
                .collect( Collectors.toList() );
    }

    @Benchmark
    @BenchmarkMode( Mode.AverageTime )
    @OutputTimeUnit( TimeUnit.SECONDS )
    public void streamMap() throws InterruptedException{
        million.stream()
                .map( v -> Integer.valueOf( v ) )
                .collect( Collectors.toList() ).size();
    }

    public static void main( String[] args ) throws RunnerException{

        Options opt = new OptionsBuilder()
                .include( TrivialBenchmarkStream.class.getSimpleName() )
                .forks( 1 )
                .warmupIterations( 5 )
                .measurementIterations( 5 )
                .threads( 1 )
                .build();

        new Runner( opt ).run();
    }

}
