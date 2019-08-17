package com.movie.index.util;

import org.testng.annotations.Test;

import com.movie.index.app.model.Movie;

public class TestGsonHelper {

  @Test
  public void testDeserializer() {
    String jsonString = "{\"tmdb_id\":10481,\"title\":\"102 Dalmatians\",\"year\":2000,"
        + "\"overview\":\"Get ready for a howling good time as an all new assortment of"
        + " irresistible animal heroes are unleashed in this great family tail! In an"
        + " unlikely alliance, the outrageous Waddlesworth... a parrot who thinks he's"
        + " a Rottweiler... teams up with Oddball... an un-marked Dalmation puppy eager"
        + " to earn her spots! Together they embark on a laugh-packed quest to outwit"
        + " the ever-scheming Cruella De Vil\",\"poster_path\":\"/dSxnIika9yWwTvEbpsmoGdeh65E.jpg\","
        + "\"poster_data_uri\":null,\"backdrop_path\":\"/2s1vbGNvXfVqWAh2rBed6bm763R.jpg\","
        + "\"vote_average\":5.1,\"vote_count\":318,\"budget\":85000000,\"revenue\":183611771,"
        + "\"genres\":[\"Comedy\",\"Family\"],\"directors\":[\"Kevin Lima\"],"
        + "\"actors\":[\"Glenn Close\",\"Ioan Gruffudd\",\"Alice Evans\",\"Tim McInnerny\","
        + "\"Gérard Depardieu\"],\"keywords\":[\"dalmatian\",\"dog\",\"london england\",\"pelz\","
        + "\"prison\",\"puppy\",\"release from prison\",\"society for the prevention of cruelty to"
        + " animals\",\"women's prison\"],\"posters\":[\"/dSxnIika9yWwTvEbpsmoGdeh65E.jpg\","
        + "\"/3ndvxCb8wHZ1yKQlzkCkwykvW9M.jpg\",\"/uBVuqjSRJ4VdPgPBNELjB37W4qt.jpg\","
        + "\"/1KSPxmdPt30RgtbEX8iBMaTW49Y.jpg\",\"/qLC4zIi9FRBia7IOqiToBzoFaBp.jpg\","
        + "\"/72sWxtHP7N2JxM9Al4oHLxO3A5B.jpg\",\"/WflC3fsWY4odeKRdtww1jbV7X9.jpg\","
        + "\"/6eKpYTmQZB3XcLJtOFtqDAKbcoz.jpg\",\"/m7ZR4w4nraJQ70s4TGRjbGbeZMd.jpg\","
        + "\"/wzxqJr55bYIftBVxuSHrInGzJEY.jpg\",\"/mVvOM6QSTy9pQIKilHcV8DbxMBI.jpg\","
        + "\"/2I3Y7vI23Y7xyhRGW3lih0GgGUM.jpg\",\"/4NZt3IJLWySZjkGMGIUFuRnyFd8.jpg\","
        + "\"/3w1wb4GNcNmhY3cqUIHGQWqadVr.jpg\",\"/fFXJM9kmErtZ4xwrSAE1xk5yPOg.jpg\","
        + "\"/vo1v6WZo6AoRfJb0uISUGAJo42B.jpg\",\"/2C9cTuPpiJPJbKvF9n4JWFLaL7g.jpg\","
        + "\"/cRKny1zffXT5llZeVGfFpDebkO9.jpg\",\"/h9rx6uaE8KyH0KXMTmKOEidcBtQ.jpg\","
        + "\"/zbV3rqZjeG4CRk8xNrZYNnIUh82.jpg\",\"/1P6KS2n9giNE8lmI43JWK4w4VHa.jpg\","
        + "\"/7eyT8dYypu5iccd8TPTmvp2weot.jpg\",\"/moHPBtN7aVLAME99iii5Jxc108D.jpg\","
        + "\"/we0UQxIS4rbGa6xWPx6BxnAhMQ7.jpg\",\"/xIwKuexGZZ1wij5G6sxSJ4t2FMW.jpg\","
        + "\"/v4oEAtPckrxxdG8BdhHHUDT9GBr.jpg\",\"/n8oRrgCuqz8xgwWOcxqjQAEM3R1.jpg\"],"
        + "\"backdrops\":[\"/2s1vbGNvXfVqWAh2rBed6bm763R.jpg\",\"/tgLLNqCcibbiUbIc2tYZh6V4XSW.jpg\"],"
        + "\"locator\":{\"type\":\"BINDER\",\"binder\":\"C\",\"page\":\"83\",\"provider\":null,"
        + "\"streaming\":false},\"has_details\":true}";

    Movie movie = GsonHelper.BASIC.fromJson(jsonString, Movie.class);
    movie.getLocator();

    jsonString = "{\"tmdb_id\":2749,\"title\":\"15 Minutes\",\"year\":2001,\"overview\":\"When "
        + "Eastern European criminals Oleg and Emil come to New York City to pick up their shar"
        + "e of a heist score, Oleg steals a video camera and starts filming their activities, "
        + "both legal and illegal. When they learn how the American media circus can make a rem"
        + "orseless killer look like the victim and make them rich, they target media-savvy NYP"
        + "D Homicide Detective Eddie Flemming and media-naive FDNY Fire Marshal Jordy Warsaw, "
        + "the cops investigating their murder and torching of their former criminal partner, f"
        + "ilming everything to sell to the local tabloid TV show 'Top Story.'\",\"poster_path"
        + "\":\"/mNG27rpZ8cVdKN2dfnGLAeE5kdl.jpg\",\"poster_data_uri\":null,\"backdrop_path\":"
        + "\"/xdha8DvElblfBbNnkpGsF42lAyv.jpg\",\"vote_average\":5.7,\"vote_count\":193,\"budge"
        + "t\":60000000,\"revenue\":56359980,\"genres\":[\"Action\",\"Crime\",\"Thriller\"],\"d"
        + "irectors\":[\"John Herzfeld\"],\"actors\":[\"Robert De Niro\",\"Charlize Theron\",\""
        + "Edward Burns\",\"Kelsey Grammer\",\"Avery Brooks\",\"Vera Farmiga\",\"Kim Cattrall\""
        + "],\"keywords\":[\"airport\",\"attempted murder\",\"breast\",\"criminal\",\"dead body"
        + "\",\"death\",\"death of boyfriend\",\"detective\",\"drunk\",\"eastern europe\",\"esc"
        + "ape\",\"ex-detainee\",\"fear\",\"female nudity\",\"fire\",\"fistfight\",\"graveyard"
        + "\",\"gun violence\",\"home invasion\",\"knife\",\"lawyer\",\"new york\",\"new york c"
        + "ity\",\"news report\",\"nypd\",\"paranoia\",\"police\",\"police detective\",\"prison"
        + "\",\"prisoner\",\"prostitute\",\"rape\",\"revenge\",\"robbery\",\"russian\",\"shot t"
        + "o death\",\"shotgun\",\"thief\",\"torture\",\"urination\",\"video\",\"video camera\""
        + ",\"violence\"],\"posters\":[\"/qqqleHV8y7NLKt7isSZAvOwVPH6.jpg\",\"/sP0IcDEOt80LdxPk"
        + "jWICPgkaHqb.jpg\",\"/pn4tcM5z5K7IDixxHOjwN0Y4Hpk.jpg\",\"/aRvlNmEbjDYF5NLXflMXl76ct4"
        + "l.jpg\",\"/3vDLrQMPajLou7hrlPKIWmn9dNb.jpg\",\"/xP3WSoac6vj2wX53sxwIkMT6lgc.jpg\",\""
        + "/zHKxfDmlOjISXA7VvGyzOp9kn1f.jpg\",\"/mNG27rpZ8cVdKN2dfnGLAeE5kdl.jpg\",\"/7JNosbPXK"
        + "yXjZTsBHA9gyoojQpn.jpg\",\"/1p4kFxTs3u6dlqLw7aLAaslOnTa.jpg\",\"/iKFeDFS5DHrDLdMTWEv"
        + "FqraxFpz.jpg\",\"/nnggwGMy2tgs4jMocZ6pu8d9rzE.jpg\",\"/qKs1tUTLFWec6YBtajOiWv4DYZl.j"
        + "pg\",\"/9Fz5gY91IYBJdZtJ4SVgxmjhR3d.jpg\",\"/4n6OrlBE0to96m0OQJCuT2hKoll.jpg\",\"/d6"
        + "yGKMso0qFZX9v2CtbTUAZLVw4.jpg\",\"/lg34b8FlK3YAtgI2XoCCZacW11x.jpg\",\"/9IprKgUKTP9d"
        + "0cq9TVp6A1FLYcR.jpg\",\"/420KgxJJzbxhNI5TNpGFp7kb7HC.jpg\",\"/fi7j9XCNgrADfecmXn8VyD"
        + "DECaQ.jpg\"],\"backdrops\":[\"/xdha8DvElblfBbNnkpGsF42lAyv.jpg\",\"/wKRsjsbuzFst3R3W"
        + "xvIM6gcVikv.jpg\",\"/8vegs85s7Ru6GiyA8qgjZKl2uXu.jpg\",\"/pL9Q2uk11vtpvDUiMhKENo5KtB"
        + ".jpg\",\"/aZBHhanBrQ4DqQ9sRz5BvHKrRL6.jpg\"],\"locator\":{\"type\":\"BINDER\",\"bind"
        + "er\":\"C\",\"page\":\"67\",\"provider\":null,\"streaming\":false},\"has_details\":true}";

    movie = GsonHelper.BASIC.fromJson(jsonString, Movie.class);
    movie.getLocator();
  }
}
