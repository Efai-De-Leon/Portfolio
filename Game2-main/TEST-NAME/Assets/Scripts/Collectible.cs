using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Collectible : MonoBehaviour
{

    public int ScoreValue  = 20;
    private GameManager gameManager; 
    // Start is called before the first frame update
    void Start()
    {
        gameManager = GameObject.Find("GameManager").GetComponent<GameManager>();
    }

    // Update is called once per frame
    void Update()
    {
        
    }
    void OnCollisionEnter(Collision collision)
    {
        if (collision.gameObject.CompareTag("Player"))
        {
            gameManager.UpdateScore(ScoreValue);
            gameObject.SetActive(false);
        }
    }

    public GameManager GetGameManager()
    {
        return gameManager;
    }
}
