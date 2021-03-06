package com.team_duck.wedgeracer;

/**
 * Created by Greg on 12/20/2015.
 */
public class TrackData
{
    public static final float scaleFactor = 1.2f;
    public static final int selectedTrack = 2; // diagonal test track

    // (169, 11)
    private static final int[] fastlane =
    {
            131,57,104,57,            104,57,78,57,            78,57,49,57,            49,57,20,57,
            20,57,-14,57,            -14,57,-45,57,            -45,57,-75,57,            -75,57,-100,32,
            -100,32,-100,8,            -100,8,-100,-16,            -100,-16,-79,-37,            -79,-37,-49,-37,
            -49,-37,-19,-37,            -19,-37,10,-37,            10,-37,40,-37,            40,-37,67,-37,
            67,-37,101,-37,            101,-37,127,-37,            127,-37,144,-37,            144,-37,170,-37,
            170,-37,186,-21,            186,-21,186,3,            186,3,186,15,            186,15,186,32,
            186,32,162,56,            162,56,161,57,            161,57,131,57,            158,31,158,17,
            158,17,158,1,            158,1,158,-12,            158,-12,151,-19,            151,-19,124,-19,
            124,-19,99,-19,            99,-19,72,-19,            72,-19,46,-19,            46,-19,19,-19,
            19,-19,-7,-19,            -7,-19,-31,-19,            -31,-19,-52,-19,            -52,-19,-66,-19,
            -66,-19,-78,-7,            -78,-7,-78,10,            -78,10,-78,26,            -78,26,-65,39,
            -65,39,-43,39,            -43,39,-20,39,            -20,39,5,39,            5,39,34,39,
            34,39,61,39,            61,39,85,39,            85,39,109,39,            109,39,132,39,
            132,39,147,39,            147,39,158,31,
    };

    // (158, 1)
    private static final int[] boxymaze =
    {
            172,-43,172,-19,            172,-19,172,10,            172,10,172,38,            172,38,139,38,
            139,38,108,38,            108,38,74,38,            74,38,40,38,            40,38,7,38,
            7,38,-25,38,            -25,38,-57,38,            -57,38,-57,8,            -57,8,-57,-19,
            -57,-19,-57,-43,            -57,-43,-37,-43,            -37,-43,-17,-43,            -17,-43,10,-43,
            10,-43,30,-43,            30,-43,50,-43,            50,-43,70,-43,            70,-43,90,-43,
            90,-43,110,-43,            110,-43,130,-43,            130,-43,150,-43,            150,-43,172,-43,
            148,25,148,4,            148,4,148,-24,            148,-24,121,-24,            121,-24,92,-24,
            92,-24,62,-24,            62,-24,28,-24,            28,-24,-4,-24,            -4,-24,-35,-24,
            -35,-24,-2,-24,            -2,-24,33,-24,            33,-24,67,-24,            67,-24,98,-24,
            98,-24,128,-24,            128,-24,148,-24,            148,-24,148,2,            148,2,148,25,
            148,25,124,25,            124,25,96,25,            96,25,64,25,            64,25,36,25,
            36,25,5,25,            5,25,-35,25,            -35,25,-4,25,            -4,25,28,25,
            28,25,60,25,            60,25,92,25,            92,25,122,25,            122,25,148,25,
            -57,1,-35,1,            -35,1,-4,1,            -4,1,30,1,            30,1,63,1,
            63,1,89,1,            89,1,111,1,            111,1,130,1,            130,1,101,1,
            101,1,73,1,            73,1,44,1,            44,1,15,1,            15,1,-12,1,
            -12,1,-34,1,            -34,1,-57,1,
    };

    // (188, 27)
    public static final int[] diagonaltesttrack = {
            203,54,183,74,            183,74,148,74,            148,74,108,74,            108,74,67,74,
            67,74,29,74,            29,74,-11,74,            -11,74,-49,74,            -49,74,-93,74,
            -93,74,-136,74,            -136,74,-160,50,            -160,50,-160,13,            -160,13,-160,-19,
            -160,-19,-160,-54,            -160,-54,-160,-88,            -160,-88,-140,-108,            -140,-108,-101,-89,
            -101,-89,-69,-73,            -69,-73,-21,-49,            -21,-49,5,-75,            5,-75,31,-101,
            31,-101,70,-81,            70,-81,106,-63,            106,-63,146,-43,            146,-43,179,-26,
            179,-26,203,-14,            203,-14,203,12,            203,12,203,33,            203,33,203,54,
            171,55,181,45,            181,45,181,20,            181,20,181,-2,            181,-2,152,-16,
            152,-16,128,-28,            128,-28,109,-38,            109,-38,82,-52,            82,-52,82,-52,
            82,-52,51,-67,            51,-67,39,-73,            39,-73,20,-54,            20,-54,1,-35,
            1,-35,-20,-14,            -20,-14,-56,-32,            -56,-32,-77,-53,            -77,-53,-125,-77,
            -125,-77,-138,-77,            -138,-77,-138,-40,            -138,-40,-138,-9,            -138,-9,-138,22,
            -138,22,-138,39,            -138,39,-127,50,            -127,50,-97,50,            -97,50,-61,50,
            -61,50,-27,50,            -27,50,11,50,            11,50,46,50,            46,50,78,50,
            78,50,78,50,            78,50,95,33,            95,33,118,33,            118,33,143,33,
            171,55,149,33,            149,33,171,55,            143,33,149,33,
    };

    public static int[] GetTrack(int trackNum)
    {
        int[][] tracks = {
                fastlane, boxymaze, diagonaltesttrack
        };

        if (trackNum < tracks.length)
        {
            return tracks[trackNum];
        }

        return null;
    }

    public static void SetStartPos(float[] pos, int trackNum)
    {
        int [][] startpos = {
                {169, 11},  // fastlane
                {158, 1}, // boxymaze
                {188, 27} // diagonaltesttrack
        };

        if (trackNum < startpos.length)
        {
            pos[0] = startpos[trackNum][0] * scaleFactor;
            pos[1] = 0.0001f;
            pos[2] = startpos[trackNum][1] * scaleFactor;
        }
    }

/*
    public static int NumTracks()
    {
        return 0;
    }

    public static Track GetTrack(int trackNum)
    {


    }

    public static Collision GetCollider(int trackNum)
    {

    }

    */
}
