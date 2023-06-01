<!DOCTYPE html>
<html>
<head>
    <title>Formulario</title>
    <link rel="stylesheet" href="styles.css">
    <style>
        :root{
            --colorTextos: #00b09b;
        }

        *,
        ::before,
        ::after{
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body{
            background: #00b09b;
            background: -webkit-linear-gradient(to right, #96c93d, #00b09b);
            background: linear-gradient(to right, #96c93d, #00b09b);

            height: 900px;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        h1{
            text-align: center;
            font-weight: 700;
        }

        p{
            text-align: center;
        }

        form{
            background: #fff;
            padding: 40px 0;
            box-shadow: 0 0 6px 0 rgba(255, 255, 255, 0.8);
            border-radius: 10px;
        }

        .form{
            width: 100%;
            margin: auto;
        }

        form .grupo{
            position: relative;
            margin: 45px;
        }

        input{
            background: none;
            color: #c6c6c6;
            font-size: 18px;
            padding: 10px 10px 10px 5px;
            display: block;
            width: 100%;
            border: none;
            border-bottom: 1px solid var(--colorTextos);
        }

        input:focus{
            outline: none;
            color:#5e5d5d;
        }

        label{
            color: var(--colorTextos);
            font-size: 16px;
            position: absolute;
            bottom: 0;
            left: 5px;
            top: 10px;
            transition: 0.5s ease-in-out all;
            pointer-events: none;
        }

        input:focus~label,
        input:valid~label{
            top: -14px;
            font-size: 12px;
            color: #00b09b;
        }

        .barra{
            position: relative;
            display: block;
            width: 100%;
        }

        .barra::before{
            content: "";
            height: 2px;
            width: 0%;
            bottom: 0;
            position: absolute;
            background: linear-gradient(to right, #96c93d, #00b09b);
            transition: 0.3s ease width;
            left: 0;
        }

        input:focus~.barra::before{
            width: 100%;
        }

        button{
            font-family: 'roboto';
            background: #00b09b;
            background: -webkit-linear-gradient(to right, #96c93d, #00b09b);
            background: linear-gradient(to right, #96c93d, #00b09b);
            border: none;
            display: block;
            width: 80%;
            margin: 10px auto;
            color: #fff;
            height: 40px;
            font-size: 16px;
            cursor: pointer;
        }

    </style>
</head>
<body>
    <header>
        <!-- Opciones de navegación -->
    </header>
            

        <form action="{{ route('formulario.guardar') }}" method="POST">
        @csrf
        <div class="form">
        <h1>Formulario </h1>
        <div class="grupo">
            <label for="precio_vivienda">Precio de la vivienda:</label>
            <input type="number" id="precio_vivienda" name="precio_vivienda" required><span class="barra"></span>
        </div>
        <div class="grupo">
            <label for="monto_necesario">¿Cuánto dinero necesitas?:</label>
            <input type="number" id="monto_necesario" name="monto_necesario" required><span class="barra"></span>
        </div>
        <div class="grupo">
            <label for="anos_hipoteca">Años de hipoteca:</label>
            <input type="number" id="anos_hipoteca" name="anos_hipoteca" required><span class="barra"></span>
        </div>
        <div class="grupo">
            <label for="tipo_vivienda">Tipo de vivienda:</label><br><br>
            <select id="tipo_vivienda" name="tipo_vivienda" required><span class="barra"></span>
                <option value="nueva">Nueva</option>
                <option value="segunda_mano">Segunda mano</option>
            </select>
        </div>
        <div class="grupo">
            <label for="uso_vivienda">Uso de la vivienda:</label><br><br>
            <select id="uso_vivienda" name="uso_vivienda" required><span class="barra"></span>
                <option value="vivienda_habitual">Vivienda habitual</option>
                <option value="segunda_vivienda">Segunda vivienda</option>
            </select>
        </div>
        <div class="grupo">
            <label for="provincia">Provincia:</label>
            <input type="text" id="provincia" name="provincia" required><span class="barra"></span>
        </div>
        <div class="grupo">
            <label for="edad">Edad:</label>
            <input type="number" id="edad" name="edad" required><span class="barra"></span>
        </div>
        <div class="grupo">
            <label for="banco">Banco:</label><br><br>
            <select id="banco" name="banco" required><span class="barra"></span>
                <option value="santander">Santander</option>
                <option value="caixa">Caixa</option>
                <option value="otros">Otros</option>
            </select>
        </div> 
            <button type="submit">Dashboard</button>
        </div>
        </form>


    
    <!-- Resto del contenido de la página -->
    
    <footer>
        <!-- Pie de página -->
    </footer>
</body>
</html>
