package br.com.rbarbioni.bluebank;

import org.junit.Test;

/**
 * Created by renan on 13/02/17.
 */
public class ApplicationTest {

    @Test(expected = IllegalArgumentException.class)
    public void main (){
        new Application().main(null);
    }
}
