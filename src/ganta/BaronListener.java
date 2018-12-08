package ganta;

public interface BaronListener {

    void onGameStart();
    void onGameOver();
    void onGameClear();
    void onHpChange(int hp);
    void onAttack();
}
