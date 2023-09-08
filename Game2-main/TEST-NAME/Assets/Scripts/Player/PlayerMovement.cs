using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerMovement : MonoBehaviour
{
    public float speed = 5f; // The speed at which the player moves
    public float x;
    public float y;
    public bool moving = false;
    public bool isFacingRight = true;
    private Animator playerAnimator;
    [SerializeField] GameManager gameManager;
    private Rigidbody rb;
    private int health = 5;
    PlayerHealthBar playerHealthBar;
    private int numOfRadars = 0;
    private int numOfProjectiles = 0;
    private int maxNumberOfProjectiles = 3;

    [SerializeField] StatusBarRadarsProjectiles statusBar; 
    enum MoveDirection
    {
        None,
        Up,
        Down,
        Left,
        Right
    }

    private MoveDirection latestDirection = MoveDirection.None;
    private bool upKeyPressed,
        downKeyPressed,
        leftKeyPressed,
        rightKeyPressed;

    void Start() 
    { 
        playerAnimator = GetComponent<Animator>();  
        rb = GetComponent<Rigidbody>();
        playerHealthBar = GetComponent<PlayerHealthBar>();
        statusBar = GetComponent<StatusBarRadarsProjectiles>();
    }

    void Update()
    {
        if(gameManager.IsGameActive())        
            GetKeyboardInput();
            GetZKeyInput();
    }

    void FixedUpdate()
    {
        if (gameManager.IsGameActive())
            MoveToDirection();
    }

    void MoveToDirection()
    {
        switch (latestDirection)
        {
            case MoveDirection.Up:
                MoveVertical(1);
                break;
            case MoveDirection.Down:
                MoveVertical(-1);
                break;
            case MoveDirection.Left:
                if (isFacingRight)
                {
                    Flip();
                    playerAnimator.SetTrigger("turning_left_t");
                    isFacingRight = false;
                }
                MoveHorizontal(-1);
                break;
            case MoveDirection.Right:
                if (!isFacingRight)
                {
                    Flip();
                    playerAnimator.SetTrigger("turning_right_t");
                    isFacingRight = true;
                }
                MoveHorizontal(1);
                break;
            default:
                moving = false;
                break;
        }
    }

    void GetKeyboardInput()
    {
        if (Input.GetKeyDown(KeyCode.UpArrow))
        {
            upKeyPressed = true;
            latestDirection = MoveDirection.Up;
        }
        else if (Input.GetKeyUp(KeyCode.UpArrow))
        {
            upKeyPressed = false;
            if (latestDirection == MoveDirection.Up)
                ResetLatestDirection();
        }

        if (Input.GetKeyDown(KeyCode.DownArrow))
        {
            downKeyPressed = true;
            latestDirection = MoveDirection.Down;
        }
        else if (Input.GetKeyUp(KeyCode.DownArrow))
        {
            downKeyPressed = false;
            if (latestDirection == MoveDirection.Down)
                ResetLatestDirection();
        }

        if (Input.GetKeyDown(KeyCode.LeftArrow))
        {
            leftKeyPressed = true;
            latestDirection = MoveDirection.Left;
        }
        else if (Input.GetKeyUp(KeyCode.LeftArrow))
        {
            leftKeyPressed = false;
            if (latestDirection == MoveDirection.Left)
                ResetLatestDirection();
        }

        if (Input.GetKeyDown(KeyCode.RightArrow))
        {
            rightKeyPressed = true;
            latestDirection = MoveDirection.Right;
        }
        else if (Input.GetKeyUp(KeyCode.RightArrow))
        {
            rightKeyPressed = false;
            if (latestDirection == MoveDirection.Right)
                ResetLatestDirection();
        }
    }

    void GetZKeyInput()
    {
        if (Input.GetKeyDown(KeyCode.Z))
        {
            UseRadar();
        }
    }

    public void UseRadar()
    {
        if (numOfRadars > 0)
        {
            Debug.Log("Num of radars: " + numOfRadars);
            numOfRadars--;
            UpdateNumOfRadars(numOfRadars);
            Debug.Log("Revealing all stuff");
            gameManager.MakeAllObjectVisible(this);
        }
        else
        {
            Debug.Log("No more radars");
        }
    }

    public int GetNumberOfRadars()
    {
        return numOfRadars;
    }

    void ResetLatestDirection()
    {
        if (upKeyPressed)
            latestDirection = MoveDirection.Up;
        else if (downKeyPressed)
            latestDirection = MoveDirection.Down;
        else if (leftKeyPressed)
            latestDirection = MoveDirection.Left;
        else if (rightKeyPressed)
            latestDirection = MoveDirection.Right;
        else
            latestDirection = MoveDirection.None;
    }

    void MoveHorizontal(float x_)
    {
        Vector3 newPosition = transform.position + new Vector3(-x_, 0, 0);
        newPosition.x = Mathf.Round(newPosition.x);
        rb.MovePosition(transform.position + new Vector3(-x_, 0, 0) * speed * Time.fixedDeltaTime);
        moving = true;
    }

    void MoveVertical(float y_)
    {
        Vector3 newPosition = transform.position + new Vector3(0, y_, 0);
        newPosition.y = Mathf.Round(newPosition.y);
        rb.MovePosition(transform.position + new Vector3(0, y_, 0) * speed * Time.fixedDeltaTime);
        moving = true;
    }

    void MoveAllDirections(float x_, float y_)
    {
        x_ *= 1.4f;
        y_ *= 1.4f;
        x_ = Mathf.Clamp(x_, -1.3f, 1.3f);
        y_ = Mathf.Clamp(y_, -1.3f, 1.3f);
        rb.MovePosition(transform.position + new Vector3(x_, y_, 0) * speed * Time.fixedDeltaTime);
    }

    void Flip() {
        transform.Rotate(0f, 180f, 0f);
    }

    void OnCollisionEnter(Collision collision)
    {
        if (collision.gameObject.CompareTag("Enemy"))
        {
            health--;
            playerHealthBar.UpdateHealthBar(health);
            collision.gameObject.GetComponent<EnemyNeedleMovementAI>().SetStop(true);
            Debug.Log(health); 
            if (health <= 0)
            {
                gameManager.SetGameComplete();
            }
        }
    }

    public void MoveVector(Vector2 moveVector)
    {
        if (moveVector.x < 0)
        {
            if (isFacingRight)
            {
                Flip();
                playerAnimator.SetTrigger("turning_left_t");
                isFacingRight = false;
            }
        }
        else if (moveVector.x > 0)
        {
            if (!isFacingRight)
            {
                Flip();
                playerAnimator.SetTrigger("turning_right_t");
                isFacingRight = true;
            }
        }
        MoveAllDirections(-moveVector.x, moveVector.y);
    }

    public int GetNumOfRadars()
    {
        return numOfRadars;
    }

    public void UpdateNumOfRadars(int numOfRadars_)
    {
        numOfRadars = numOfRadars_;
        statusBar.UpdateNumOfRadars(numOfRadars);
    }

    public int GetNumOfEraser()
    {
        return numOfProjectiles;
    }

    public void UpdateNumOfEraser(int numOfProjectiles)
    {
        this.numOfProjectiles = numOfProjectiles;
        statusBar.UpdateNumOfProjectiles(numOfProjectiles);
    }

    public int GetMaxNumOfEraser()
    {
        return maxNumberOfProjectiles;
    }
}
