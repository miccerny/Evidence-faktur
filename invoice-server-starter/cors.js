const cors = require("cors");

module.exports = function(app) {
    app.use(cors({
        // odpoví, že adresa našeho react serveru je ok
        origin: true,
        // umožní používat cookies i při CORS requestu
        credentials: true
    }));
};