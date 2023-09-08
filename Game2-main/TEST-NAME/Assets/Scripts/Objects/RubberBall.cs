using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RubberBall : MonoBehaviour
{
    public bool collideWCotton = false;
    public int numOfCollisions = 0;
    public bool collidesWithRubberBall = false;        

    void OnCollisionEnter(Collision collision) {
        if (collision.gameObject.CompareTag("Cotton") && numOfCollisions < 4)
        {
            collideWCotton = true;
            Destroy(collision.gameObject);
            numOfCollisions++;
        }

        if (collision.gameObject.CompareTag("RubberBall"))
        {
            Debug.Log("Rubber ball collided with another rubber ball 2");
            collidesWithRubberBall = true;
        }  
    }

    public bool CollidesWithRubberBall()
    {
        return collidesWithRubberBall;
    }
}
