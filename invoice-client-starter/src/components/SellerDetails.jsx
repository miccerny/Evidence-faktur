
function SellerDetails({seller}){
    return(
        <div className="seller-details">
            {/* DIČ prodejce */}
                 <p>
                    <strong>DIČ:</strong>
                    <br />
                    {seller.taxNumber}
                </p>
                {/* Bankovní účet a kód banky */}
                <p>
                    <strong>Bankovní účet:</strong>
                    <br />
                    {seller.accountNumber}/{seller.bankCode} 
                </p>
                {/* IBAN číslo */}
                <p>
                    <strong>IBAN:</strong><br/>
                    ({seller.iban})
                </p>
                {/* Telefonní číslo */}
                <p>
                    <strong>Tel.:</strong>
                    <br />
                    {seller.telephone}
                </p>
                {/* Emailová adresa */}
                <p>
                    <strong>Mail:</strong>
                    <br />
                    {seller.mail}
                </p>
                {/* Sídlo: ulice, město, PSČ, země */}
                <p>
                    <strong>Sídlo:</strong>
                    <br />
                    {seller.street}, {seller.city} <br/>
                    {seller.zip}, {seller.country}
                </p>
                {/* Poznámka k prodejci */}
                <p>
                    <strong>Poznámka:</strong>
                    <br />
                    {seller.note}
                </p>
                </div>
    );
};
export default SellerDetails;