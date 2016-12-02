package steam;

import entity.Order;
import entity.Person;
import entity.Product;
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
 * Created by Zlobniy on 02.12.2016.
 */
@State( Scope.Benchmark )
public class TrivialBenchmarkStream {

    private final List<Order> orders = new ArrayList<>(  );
    private Map<String, String> map;
    private List<Integer> million;
    private List<Person> someData;

    @Setup
    public void setup() throws Exception{

        orders.add( new Order( new Product( "collections 1" ), new Product( "collections 2" ) ) );
        orders.add( new Order( new Product( "collections 3" ), new Product( "collections 4" ), new Product( "collections 5" ) ) );

        map = new HashMap<>();
        for( int i = 0; i < 1000; i++ ){
            map.put( "i", "" + i );
        }

        million = Stream.generate(new Random()::nextInt)
                .limit(1000000)
                .map( Integer::valueOf )
                .collect( Collectors.toList() );

        someData = new ArrayList<>(  );
        someData.add( new Person( "Vasia", "Pupkin", LocalDate.now() ) );
        someData.add( new Person( "Petia", "Pupkin", LocalDate.now() ) );
        someData.add( new Person( "Petia", "Ivanov", LocalDate.now() ) );

    }


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
        someData.stream().map( s -> s.getName() + "_" + s.getSurName() ).collect( Collectors.toList() );
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
