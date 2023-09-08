using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EraserPickup : MonoBehaviour
{
    private Pool eraserPool;
    private PickUpEraserSpawnManager pickUpEraserSpawnManager;
    // Start is called before the first frame update
    void Start()
    {
        eraserPool = GameObject.FindWithTag("SpawnPickUpErasers").GetComponent<Pool>();
        pickUpEraserSpawnManager = GameObject.FindWithTag("SpawnPickUpErasers").GetComponent<PickUpEraserSpawnManager>();
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    void OnCollisionEnter(Collision collision)
    {
        if (collision.gameObject.CompareTag("Player"))
        {
            pickUpEraserSpawnManager.DecreaseCurrentNumOfErasers();
            Debug.Log("eraser collected by player");
            PlayerMovement playerMovementScript = collision.gameObject.GetComponent<PlayerMovement>();
            playerMovementScript.UpdateNumOfEraser(playerMovementScript.GetNumOfEraser() + 1);
            eraserPool.ReturnObject(gameObject);
        }
    }
}
