using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RotateWheel : MonoBehaviour
{
    private PlayerMovement playerMovementScript;
    private float moveWheelSpeed = 8f;
    // Update is called once per frame
    void Start()
    {
        // Get the PlayerMovement script from the Player object
        playerMovementScript = GameObject.Find("Player").GetComponent<PlayerMovement>();
    }
    
    void Update()
    {
        if (playerMovementScript.moving && playerMovementScript.isFacingRight) 
        {
            transform.Rotate(Vector3.up * moveWheelSpeed);    
        }
        else if (playerMovementScript.moving && !playerMovementScript.isFacingRight)
        {
            transform.Rotate(Vector3.down * moveWheelSpeed);
        }
    }
}
