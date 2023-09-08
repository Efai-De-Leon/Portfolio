using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PickUpEraserSpawnManager : MonoBehaviour
{
    [SerializeField] PlayerMovement player;
    private RandomCoordinatesGenerator randomCoordinatesGenerator;
    private Pool pool;
    private Vector3 RandomPosition;
    private int currentNumOfEraser = 0; 
    // Start is called before the first frame update
    void Start()
    {
        randomCoordinatesGenerator = new RandomCoordinatesGenerator();
        pool = GetComponent<Pool>();
        StartCoroutine(SpawnEraserPickUpCoroutine());

    }

    // Update is called once per frame
    void Update(){}

    public void SpawnEraserPickUp()
    {
        currentNumOfEraser++;
        GameObject eraserPickUp = pool.GetObject();
        RandomPosition = randomCoordinatesGenerator.GetRandomPosition();
        eraserPickUp.transform.position = RandomPosition;
    }

    IEnumerator SpawnEraserPickUpCoroutine()
    {
        while (true)
        {
            yield return new WaitForSeconds(3.0f);
            if (currentNumOfEraser < player.GetMaxNumOfEraser()) 
                SpawnEraserPickUp();
        }
    }

    public void DecreaseCurrentNumOfErasers()
    {
        currentNumOfEraser--;
    }
}
