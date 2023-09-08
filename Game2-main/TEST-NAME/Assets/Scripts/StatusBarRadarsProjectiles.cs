using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;
using UnityEngine.UI;
public class StatusBarRadarsProjectiles : MonoBehaviour
{
    [SerializeField] TextMeshProUGUI numOfRadarsText;
    [SerializeField] TextMeshProUGUI numOfProjectilesText;
    public void Start()
    {
        numOfRadarsText.text = "Radars: 0";
        numOfProjectilesText.text = "Projectiles: 0";
    }
    public void UpdateNumOfRadars(int _numOfRadars)
    {
        numOfRadarsText.text = "Radars: " + _numOfRadars.ToString(); 
    }

    public void UpdateNumOfProjectiles(int _numOfProjectiles)
    {
        numOfProjectilesText.text = "Projectiles: " + _numOfProjectiles.ToString(); 
    }
}
