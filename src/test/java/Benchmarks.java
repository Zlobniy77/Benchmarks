import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import steam.TrivialBenchmarkStream;

/**
 * Created by Aleksandr on 02.12.2016.
 */
public class Benchmarks {

    public static void main( String[] args ) throws RunnerException{

        Options opt = new OptionsBuilder()
                .include( TrivialBenchmarkStream.class.getSimpleName() )
                .forks( 1 )
                .warmupIterations( 5 )
                .measurementIterations( 5 )
                .resultFormat( ResultFormatType.TEXT )
                .threads( 1 )
                .build();

        new Runner( opt ).run();
    }

}
