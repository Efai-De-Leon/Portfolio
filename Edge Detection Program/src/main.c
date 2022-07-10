#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define ROWS    (int)480
#define COLUMNS    (int)640

void clear1( unsigned char image[][COLUMNS] );
void header( int row, int col, unsigned char head[32] );
int sobelOpX(int section[3][3]);
int sobelOpY(int section[3][3]);

int main( int argc, char **argv )
{

  int        i, j, k, threshold[3], max[3], x, y, s, temp[3][3], xTempImage[ROWS][COLUMNS], yTempImage[ROWS][COLUMNS];
  FILE      *fp;
  unsigned char  image[ROWS][COLUMNS], ximage[ROWS][COLUMNS], yimage[ROWS][COLUMNS], simage[ROWS][COLUMNS], bimage[ROWS][COLUMNS], head[32];
  char      filename[6][50], ifilename[50], ch;
  int       brightness;
  
  strcpy( filename[0], "image1" );
  strcpy( filename[1], "image2" );
  strcpy( filename[2], "image3" );
  header ( ROWS, COLUMNS, head );

  threshold[0] = 56;
  threshold[1] = 2;
  threshold[2] = 7;
  
  printf( "Maximum of Gx, Gy, SGM\n" );
  for ( k = 0; k < 3; k ++)
  {
    clear1( ximage );
    clear1( yimage );

    /* Read in the image */
    strcpy( ifilename, filename[k] );
    if (( fp = fopen( strcat(ifilename, ".raw"), "rb" )) == NULL )
    {
      fprintf( stderr, "error: couldn't open %s\n", ifilename );
      exit( 1 );
    }
    for ( i = 0; i < ROWS ; i++ )
      if ( fread( image[i], sizeof(char), COLUMNS, fp ) != COLUMNS )
      {
      fprintf( stderr, "error: couldn't read enough stuff\n" );
      exit( 1 );
      }
    fclose( fp );

    max[0] = 0; //maximum of Gx
    max[1] = 0; //maximum of Gy
    max[2] = 0; //maximum of SGM

    /* Compute Gx, Gy, SGM, find out the maximum and normalize*/
    
    /* Applying sobel operator Gx*/
    // find the maxium value
    for(i = 1; i < ROWS - 1; i++){
      for(j = 1; j < COLUMNS - 1; j++){
        for(int k = 0; k < 3; k++){
          for(int l = 0; l < 3; l++){
            temp[k][l] = image[i - 1 + k][j - 1 + l];
          }
        }
        s = sobelOpX(temp);
        if(s > max[0]){
          max[0] = s;
        }
      }
    }
    // Normalization
    for(i = 1; i < ROWS - 1; i++){
      for(j = 1; j < COLUMNS - 1; j++){
        for(int k = 0; k < 3; k++){
          for(int l = 0; l < 3; l++){
            temp[k][l] = image[i - 1 + k][j - 1 + l];
          }
        }
        s = sobelOpX(temp);
        xTempImage[i][j] = s;   // values before normalization
        ximage[i][j] = s*255.0/(float)max[0];
      }
    }
  
    
    
    
    /* Applying sobel operator Gy*/
    // find the maxium value
    for(i = 1; i < ROWS - 1; i++){
      for(j = 1; j < COLUMNS - 1; j++){
        for(int k = 0; k < 3; k++){
          for(int l = 0; l < 3; l++){
            temp[k][l] = image[i - 1 + k][j - 1 + l];
          }
        }
        s = sobelOpY(temp);
        if(s > max[1]){
          max[1] = s;
        }
      }
    }
    // Normalization
    for(i = 1; i < ROWS - 1; i++){
      for(j = 1; j < COLUMNS - 1; j++){
        for(int k = 0; k < 3; k++){
          for(int l = 0; l < 3; l++){
            temp[k][l] = image[i - 1 + k][j - 1 + l];
          }
        }
        s = sobelOpY(temp);
        yTempImage[i][j] = s;   // values before normalization
        yimage[i][j] = s*255.0/(float)max[1];
      }
    }
    // Making the border  equal to 0
    for(i = 0; i < ROWS; i++){
      for(j = 0; j < COLUMNS; j++){
        if((i == 0) || (i == (ROWS - 1)) || (j == 0) || (j == (COLUMNS - 1))){
           yimage[i][j] = 0;
           yTempImage[i][j] = 0;
           ximage[i][j] = 0;
           xTempImage[i][j] = 0;
        }
      }
    }
    
    
    
    
    // Computing SGM
    // Find the maximum value
    for(i = 1; i < ROWS - 1; i++){
      for(j = 1; j < COLUMNS - 1; j++){
        s = ((xTempImage[i][j]) * (xTempImage[i][j])) + (yTempImage[i][j] * yTempImage[i][j]);
        if(s > max[2]){
          max[2] = s;
        }
      }
    }
    // Normalization
    for(i = 1; i < ROWS - 1; i++){
      for(j = 1; j < COLUMNS - 1; j++){
        s = ((xTempImage[i][j]) * (xTempImage[i][j])) + (yTempImage[i][j] * yTempImage[i][j]);
        simage[i][j] = s*255.0/(double)max[2];
      }
    }
    
    
    
    // Compute the binary image for SGM
    for( i = 0; i < ROWS; i++){
         for( j = 0; j < COLUMNS; j++){
             brightness = simage[i][j];
             if(brightness > threshold[k]){
                 bimage[i][j] = 255;
             }
             else{
                 bimage[i][j] = 0;
             }
         }
     }
    
    

    /* Write Gx to a new image*/
    strcpy( ifilename, filename[k] );
    if (!( fp = fopen( strcat( ifilename, "-x.ras" ), "wb" ) ))
    {
      fprintf( stderr, "error: could not open %s\n", ifilename );
      exit( 1 );
    }
    fwrite( head, 4, 8, fp );
    for ( i = 0 ; i < ROWS ; i++ ) fwrite( ximage[i], 1, COLUMNS, fp );
    fclose( fp );
          
    
    
    /* Write Gy to a new image */
    strcpy( ifilename, filename[k] );
    if (!( fp = fopen( strcat( ifilename, "-y.ras" ), "wb" ) ))
    {
      fprintf( stderr, "error: could not open %s\n", ifilename );
      exit( 1 );
    }
    fwrite( head, 4, 8, fp );
    for ( i = 0 ; i < ROWS ; i++ ) fwrite( yimage[i], 1, COLUMNS, fp );
    fclose( fp );

    

    /* Write SGM to a new image */
    strcpy( ifilename, filename[k] );
    if (!( fp = fopen( strcat( ifilename, "-s.ras" ), "wb" ) ))
    {
      fprintf( stderr, "error: could not open %s\n", ifilename );
      exit( 1 );
    }
    fwrite( head, 4, 8, fp );
    for ( i = 0 ; i < ROWS ; i++ ) fwrite( simage[i], 1, COLUMNS, fp );
    fclose( fp );
    
    
    /* Write the binary image to a new image */
    strcpy( ifilename, filename[k] );
    if (!( fp = fopen( strcat( ifilename, "-b.ras" ), "wb" ) ))
    {
      fprintf( stderr, "error: could not open %s\n", ifilename );
      exit( 1 );
    }
    fwrite( head, 4, 8, fp );
    for ( i = 0 ; i < ROWS ; i++ ) fwrite( bimage[i], 1, COLUMNS, fp );
    fclose( fp );

    printf( "%d %d %d\n", max[0], max[1], max[2] );

  }

  printf( "Press any key to exit: " );
  gets( &ch );
  return 0;
}

int sobelOpX(int section[3][3]){
  int sum = 0;
  int dX[3][3] = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
  for(int i = 0; i < 3; i++){
    for(int j = 0; j < 3; j++){
      sum += (dX[i][j]*section[i][j]);
    }
  }
  return abs(sum);
}

int sobelOpY(int section[3][3]){
  int sum = 0;
  int dY[3][3] = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
  for(int i = 0; i < 3; i++){
    for(int j = 0; j < 3; j++){
      sum += (dY[i][j]*section[i][j]);
    }
  }
  return abs(sum);
}


void clear1( unsigned char image[][COLUMNS] )
{
  int  i,j;
  for ( i = 0 ; i < ROWS ; i++ )
    for ( j = 0 ; j < COLUMNS ; j++ ) image[i][j] = 0;
}

void header( int row, int col, unsigned char head[32] )
{
  int *p = (int *)head;
  char *ch;
  int num = row * col;

  /* Choose little-endian or big-endian header depending on the machine. Don't modify this */
  /* Little-endian for PC */
  
  *p = 0x956aa659;
  *(p + 3) = 0x08000000;
  *(p + 5) = 0x01000000;
  *(p + 6) = 0x0;
  *(p + 7) = 0xf8000000;

  ch = (char*)&col;
  head[7] = *ch;
  ch ++;
  head[6] = *ch;
  ch ++;
  head[5] = *ch;
  ch ++;
  head[4] = *ch;

  ch = (char*)&row;
  head[11] = *ch;
  ch ++;
  head[10] = *ch;
  ch ++;
  head[9] = *ch;
  ch ++;
  head[8] = *ch;
  
  ch = (char*)&num;
  head[19] = *ch;
  ch ++;
  head[18] = *ch;
  ch ++;
  head[17] = *ch;
  ch ++;
  head[16] = *ch;
  

  /* Big-endian for unix */
  /*
  *p = 0x59a66a95;
  *(p + 1) = col;
  *(p + 2) = row;
  *(p + 3) = 0x8;
  *(p + 4) = num;
  *(p + 5) = 0x1;
  *(p + 6) = 0x0;
  *(p + 7) = 0xf8;
*/
}
