function BuyerDeatils({ buyer }) {
    return (
        <div className="buyer-details">
            {/* DIČ prodejce */}
            <p>
                <strong>DIČ:</strong>
                <br />
                {buyer.taxNumber}
            </p>
            {/* Bankovní účet a kód banky */}
            <p>
                <strong>Bankovní účet:</strong>
                <br />
                {buyer.accountNumber}/{buyer.bankCode}
            </p>
            {/* IBAN číslo */}
            <p>
                <strong>IBAN:</strong><br />
                ({buyer.iban})
            </p>
            {/* Telefonní číslo */}
            <p>
                <strong>Tel.:</strong>
                <br />
                {buyer.telephone}
            </p>
            {/* Emailová adresa */}
            <p>
                <strong>Mail:</strong>
                <br />
                {buyer.mail}
            </p>
            {/* Sídlo: ulice, město, PSČ, země */}
            <p>
                <strong>Sídlo:</strong>
                <br />
                {buyer.street}, {buyer.city} <br />
                {buyer.zip}, {buyer.country}
            </p>
            {/* Poznámka k prodejci */}
            <p>
                <strong>Poznámka:</strong>
                <br />
                {buyer.note}
            </p>
        </div>
    );
};
export default BuyerDeatils;