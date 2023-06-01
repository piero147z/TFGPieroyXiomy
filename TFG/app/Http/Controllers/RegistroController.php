<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use GuzzleHttp\Client;
use Symfony\Component\DomCrawler\Crawler;
use Illuminate\Support\Facades\DB;
use App\Models\HipotecaFija;

class RegistroController extends Controller
{
    //
    public function registro(Request $request)
    {
        // Aquí puedes realizar cualquier lógica de registro que necesites
        $nombre = $request->input('nombre');
        $apellido = $request->input('apellido');
        $usuario = $request->input('usuario');
        $contrasena = $request->input('password');

        // Verificar si el usuario ya existe en la tabla
        $usuarioExistente = DB::table('tabla_login')->where('usuario', $usuario)->first();

        if ($usuarioExistente) {
            return "Usuario ya existe";
        }

        // Insertar el nuevo usuario en la tabla
        DB::table('tabla_login')->insert([
            'nombre' => $nombre,
            'apellido' => $apellido,
            'usuario' => $usuario,
            'contrasena' => $contrasena
        ]);

        // Realizar scraping y obtener los datos/////////////////
        $scrapedData = $this->scrapeData();

         // Obtener los valores individuales
         $banco1 = $scrapedData['banco1'];
         $banco2 = $scrapedData['banco2'];
         $banco3 = $scrapedData['banco3'];

         $TIN = $scrapedData['TIN'];
         $TIN2 = $scrapedData['TIN2'];
         $TIN3 = $scrapedData['TIN3'];

         $TAE = $scrapedData['TAE'];
         $TAE2 = $scrapedData['TAE2'];
         $TAE3 = $scrapedData['TAE3'];

         // Verificar si los registros ya existen en la tabla
        $existingRecords = DB::table('hipoteca_fija')
        ->whereIn('banco', [$banco1, $banco2, $banco3])
        ->count();

        // Insertar registros si no existen
        HipotecaFija::firstOrCreate(['banco' => $banco1], [
            'TIN' => $TIN,
            'TAE' => $TAE,
        ]);

        HipotecaFija::firstOrCreate(['banco' => $banco2], [
            'TIN' => $TIN2,
            'TAE' => $TAE2,
        ]);

        HipotecaFija::firstOrCreate(['banco' => $banco3], [
            'TIN' => $TIN3,
            'TAE' => $TAE3,
        ]);

        

        // Redirige al usuario a la vista del formulario y pasa los datos como variables a la vista
        return view('formulario', compact('banco1', 'banco2','banco3','TIN','TIN2','TIN3','TAE','TAE2','TAE3'));
    
    }


    private function scrapeData()
    {
        // Realizar solicitud HTTP
        $client = new Client();
        $response = $client->request('GET', 'https://www.kelisto.es/hipotecas');

        // Obtener contenido de la respuesta
        $html = $response->getBody()->getContents();

        // Utilizar DomCrawler para extraer datos
        $crawler = new Crawler($html);
        
        //Banco 
        $selector = '#__next > div > section:nth-child(3) > div > div > div:nth-child(2) > div > div.Tabs_tabs__tab-content__c0YTa.Tabs_tabs__tab-content--visible__hyVc5 > div > div:nth-child(2) > div > div:nth-child(1) > span.CardLayout_card-layout__header__p0KJh > div > div > div.CardHeader_card-header__header__data_wrapper__O_v4B > a > span';
        $banco1 = $crawler->filter($selector)->text();

        $selector2 = '#__next > div > section:nth-child(3) > div > div > div:nth-child(2) > div > div.Tabs_tabs__tab-content__c0YTa.Tabs_tabs__tab-content--visible__hyVc5 > div > div:nth-child(2) > div > div:nth-child(2) > span.CardLayout_card-layout__header__p0KJh > div > div > div.CardHeader_card-header__header__data_wrapper__O_v4B > a > span';
        $banco2 = $crawler->filter($selector2)->text();

        $selector3 = '#__next > div > section:nth-child(3) > div > div > div:nth-child(2) > div > div.Tabs_tabs__tab-content__c0YTa.Tabs_tabs__tab-content--visible__hyVc5 > div > div:nth-child(2) > div > div:nth-child(3) > span.CardLayout_card-layout__header__p0KJh > div > div > div.CardHeader_card-header__header__data_wrapper__O_v4B > a > span';
        $banco3 = $crawler->filter($selector3)->text();

        //TIN 
        $selector4 = '#__next > div > section:nth-child(3) > div > div > div:nth-child(2) > div > div.Tabs_tabs__tab-content__c0YTa.Tabs_tabs__tab-content--visible__hyVc5 > div > div:nth-child(2) > div > div:nth-child(1) > span.CardLayout_card-layout__table__Rl_O4 > div > div.MortgagesTable_mortgages-table__cell-3__7evFm > span';
        $TIN = $crawler->filter($selector4)->text();
        $TIN = preg_replace('/[^0-9.]/', '', $TIN);
        $TIN = number_format(floatval($TIN)/10000,4);

        $selector5 = '#__next > div > section:nth-child(3) > div > div > div:nth-child(2) > div > div.Tabs_tabs__tab-content__c0YTa.Tabs_tabs__tab-content--visible__hyVc5 > div > div:nth-child(2) > div > div:nth-child(2) > span.CardLayout_card-layout__table__Rl_O4 > div > div.MortgagesTable_mortgages-table__cell-3__7evFm > span';
        $TIN2 = $crawler->filter($selector5)->text();
        $TIN2 = preg_replace('/[^0-9.]/', '', $TIN2);
        $TIN2 = number_format(floatval($TIN2)/10000,4);
        

        $selector6 = '#__next > div > section:nth-child(3) > div > div > div:nth-child(2) > div > div.Tabs_tabs__tab-content__c0YTa.Tabs_tabs__tab-content--visible__hyVc5 > div > div:nth-child(2) > div > div:nth-child(3) > span.CardLayout_card-layout__table__Rl_O4 > div > div.MortgagesTable_mortgages-table__cell-3__7evFm > span';
        $TIN3 = $crawler->filter($selector6)->text();
        $TIN3 = preg_replace('/[^0-9.]/', '', $TIN3);
        $TIN3 = number_format(floatval($TIN3)/10000,4);

        //TAE
        $selector7 = '#__next > div > section:nth-child(3) > div > div > div:nth-child(2) > div > div.Tabs_tabs__tab-content__c0YTa.Tabs_tabs__tab-content--visible__hyVc5 > div > div:nth-child(2) > div > div:nth-child(1) > span.CardLayout_card-layout__table__Rl_O4 > div > div.MortgagesTable_mortgages-table__cell-5__2WQn2 > span';
        $TAE = $crawler->filter($selector7)->text();
        $TAE = preg_replace('/[^0-9.]/', '', $TAE);
        $TAE = number_format(floatval($TAE)/10000,4);

        $selector8 = '#__next > div > section:nth-child(3) > div > div > div:nth-child(2) > div > div.Tabs_tabs__tab-content__c0YTa.Tabs_tabs__tab-content--visible__hyVc5 > div > div:nth-child(2) > div > div:nth-child(2) > span.CardLayout_card-layout__table__Rl_O4 > div > div.MortgagesTable_mortgages-table__cell-5__2WQn2 > span';
        $TAE2 = $crawler->filter($selector8)->text();
        $TAE2 = preg_replace('/[^0-9.]/', '', $TAE2);
        $TAE2 = number_format(floatval($TAE2)/10000,4);

        $selector9 = '#__next > div > section:nth-child(3) > div > div > div:nth-child(2) > div > div.Tabs_tabs__tab-content__c0YTa.Tabs_tabs__tab-content--visible__hyVc5 > div > div:nth-child(2) > div > div:nth-child(3) > span.CardLayout_card-layout__table__Rl_O4 > div > div.MortgagesTable_mortgages-table__cell-5__2WQn2 > span';
        $TAE3 = $crawler->filter($selector9)->text();
        // Eliminar los caracteres no numéricos y el símbolo "%"
        $TAE3 = preg_replace('/[^0-9.]/', '', $TAE3);
        $TAE3 = number_format(floatval($TAE3)/10000,4);


        return [
            'banco1' => $banco1,
            'banco2' => $banco2,
            'banco3' => $banco3,

            'TIN' => $TIN,
            'TIN2' => $TIN2,
            'TIN3' => $TIN3,

            'TAE' => $TAE,
            'TAE2' => $TAE2,
            'TAE3' => $TAE3,
        ];
    }


}
