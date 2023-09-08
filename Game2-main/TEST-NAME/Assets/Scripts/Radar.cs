using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Radar : MonoBehaviour
{
    void OnCollisionEnter(Collision collision)
    {
        if (collision.gameObject.CompareTag("Player"))
        {
            PlayerMovement playerMovementScript = collision.gameObject.GetComponent<PlayerMovement>();
            playerMovementScript.UpdateNumOfRadars(playerMovementScript.GetNumOfRadars() + 1);
            Debug.Log("collected by player");
        }
    }
}
