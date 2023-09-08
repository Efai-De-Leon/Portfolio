using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ClothButton : MonoBehaviour
{
    // Start is called before the first frame update
    private Collectible collectibleScript;
    private GameManager gameManager;
    
    void Start()
    {
        collectibleScript = GetComponent<Collectible>();
        gameManager = collectibleScript.GetGameManager();
    }

    // Update is called once per frame
    void Update()
    {
    }

    void OnCollisionEnter(Collision collision)
    {
        if (collision.gameObject.CompareTag("Player"))
        {
            gameManager.UpdateButtonsCollected();
            if (gameManager.GetButtonsCollected() == 3)
            {
                gameManager.SetGameComplete();
            }
        }
    }
}
