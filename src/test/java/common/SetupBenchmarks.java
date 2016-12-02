package common;

import entity.Order;
import entity.Person;
import entity.Product;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.RunnerException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Aleksandr on 02.12.2016.
 */
@State( Scope.Benchmark )
public class SetupBenchmarks {

    protected volatile double x = Math.PI;
    protected final List<Order> orders = new ArrayList<>(  );

    protected Map<String, String> map;
    protected List<Integer> million;
    protected List<Person> someData;

    protected int v = 1;
    protected volatile int b = 1;

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

        someData = generateSomeData();

    }

    private List<Person> generateSomeData(){

        return Stream.generate( new Random()::nextInt )
                .limit( 1000 )
                .map( i -> new Person( "name_", i.toString(), LocalDate.now() ) )
                .collect( Collectors.toList() );

    }

    public static void main( String[] args ) throws RunnerException{

        SetupBenchmarks setup = new SetupBenchmarks();
        List<Person> someData = setup.generateSomeData();
        System.out.println( someData.size() );

    }

}
