
const CANVAS_WIDTH = 300;
const CANVAS_HEIGHT = 300;
const SCALE = 25;
const CENTER_X = CANVAS_WIDTH / 2;
const CENTER_Y = CANVAS_HEIGHT / 2;


window.currentR = 0;

function drawGraph(r, pointsJson) {
    const canvas = document.getElementById("graphCanvas");
    if (!canvas) return;
    const ctx = canvas.getContext("2d");

    ctx.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

    if (r > 0) {
        ctx.fillStyle = "#4a90e2";
        const rPx = r * SCALE;
        const halfRPx = (r / 2) * SCALE;


        ctx.beginPath();
        ctx.fillRect(CENTER_X, CENTER_Y - rPx, rPx, rPx);


        ctx.beginPath();
        ctx.moveTo(CENTER_X, CENTER_Y);
        ctx.lineTo(CENTER_X - halfRPx, CENTER_Y);
        ctx.lineTo(CENTER_X, CENTER_Y - halfRPx);
        ctx.fill();

        ctx.beginPath();
        ctx.moveTo(CENTER_X, CENTER_Y);
        ctx.arc(CENTER_X, CENTER_Y, halfRPx, 0.5 * Math.PI, Math.PI);
        ctx.fill();
    }


    ctx.strokeStyle = "black";
    ctx.lineWidth = 1;
    ctx.beginPath();
    ctx.moveTo(0, CENTER_Y); ctx.lineTo(CANVAS_WIDTH, CENTER_Y);
    ctx.moveTo(CENTER_X, 0); ctx.lineTo(CENTER_X, CANVAS_HEIGHT);
    ctx.stroke();


    ctx.fillStyle = "black";
    ctx.font = "10px Arial";

    for (let i = -5; i <= 5; i++) {
        if (i === 0) continue;

        ctx.beginPath();
        ctx.moveTo(CENTER_X + i * SCALE, CENTER_Y - 3);
        ctx.lineTo(CENTER_X + i * SCALE, CENTER_Y + 3);
        ctx.stroke();
        ctx.fillText(i, CENTER_X + i * SCALE - 3, CENTER_Y + 12);


        ctx.beginPath();
        ctx.moveTo(CENTER_X - 3, CENTER_Y - i * SCALE);
        ctx.lineTo(CENTER_X + 3, CENTER_Y - i * SCALE);
        ctx.stroke();
        ctx.fillText(i, CENTER_X + 8, CENTER_Y - i * SCALE + 3);
    }

    ctx.fillText("Y", CENTER_X + 6, 10);
    ctx.fillText("X", CANVAS_WIDTH - 10, CENTER_Y - 5);

    if (pointsJson) {
        pointsJson.forEach(p => {
            const xPx = CENTER_X + p.x * SCALE;
            const yPx = CENTER_Y - p.y * SCALE;

            ctx.beginPath();
            ctx.arc(xPx, yPx, 4, 0, 2 * Math.PI);

            ctx.fillStyle = p.hit ? "#32CD32" : "#DC143C";
            ctx.fill();

        });
    }
}

function handleCanvasClick(event) {
    const r = window.currentR;

    if (!r || r <= 0) {
        alert("у тебя R не выбрана!");
        return;
    }

    const canvas = document.getElementById("graphCanvas");
    const rect = canvas.getBoundingClientRect();
    const clickX = event.clientX - rect.left;
    const clickY = event.clientY - rect.top;

    const xVal = (clickX - CENTER_X) / SCALE;
    const yVal = (CENTER_Y - clickY) / SCALE;

    sendGraphClick([
        {name:'x', value: xVal.toFixed(4)},
        {name:'y', value: yVal.toFixed(4)},
        {name:'r', value: r}
    ]);
}