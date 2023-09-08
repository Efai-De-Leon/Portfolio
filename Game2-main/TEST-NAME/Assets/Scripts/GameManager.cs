using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class GameManager : MonoBehaviour
{
    [SerializeField] TextMeshProUGUI scoreText;
    [SerializeField] TextMeshProUGUI gameCompleteText;
    [SerializeField] Button restartButton;
    [SerializeField] ObjectsSpawnManager objectsSpawnManager; 
    private int score;
    private int buttonsCollected = 0;
    private bool isGameActive;
    private int numOfEnemiesOnScreen = 0;
    // Start is called before the first frame update
    void Start()
    {
        score = 0;
        scoreText.text = "Score: " + score.ToString();
        isGameActive = true;
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public void UpdateScore(int _score)
    {
        score += _score;
        scoreText.text = "Score: " + score.ToString();
    }

    public void SetGameComplete()
    {
        gameCompleteText.gameObject.SetActive(true);
        restartButton.gameObject.SetActive(true);
        isGameActive = false;
    }

    public void UpdateButtonsCollected()
    {
        buttonsCollected++;
        Debug.Log(buttonsCollected);
    }

    public int GetButtonsCollected()
    {
        return buttonsCollected;
    }

    public bool IsGameActive()
    {
        return isGameActive;
    }

    public void RestartGame()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().name);
    }

    public void UpdateNumOfEnemiesOnScreen(int numOfEnemies)
    {
        numOfEnemiesOnScreen += numOfEnemies;
    }

    public int GetNumOfEnemiesOnScreen()
    {
        return numOfEnemiesOnScreen;
    }

    public void MakeAllObjectVisible(PlayerMovement playerMovement)
    {
        objectsSpawnManager.MakeAllObjectVisible(playerMovement);
    }
}
