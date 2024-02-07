package com.samuel.oremoschangana.apresentacaoOracao

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuel.oremoschangana.dataOracao.Oracao
import com.samuel.oremoschangana.dataOracao.OracaoDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class OracoesViewModel( private val dao: OracaoDao): ViewModel() {

    private val isSortedById = MutableStateFlow(true)

    @OptIn(ExperimentalCoroutinesApi::class)
    private var oracoes =
        isSortedById.flatMapLatest{ sort ->
            if (sort){
                dao.getOracaOrderById()
            }else{
                dao.getOracaOrderById()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val _state = MutableStateFlow(OracaoState())

    val state =
        combine(_state, isSortedById, oracoes){state, isSortedById, oracoes ->
            state.copy(
                oracoes = oracoes
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), OracaoState())

    init {
        viewModelScope.launch {
            val listaOracoes = dao.getOracaOrderById().first()
            if (listaOracoes.isEmpty()) {
                inserirDadosAutomaticamente()
            }
        }
    }

    private fun inserirDadosAutomaticamente() {
        val oracoesParaInserir = listOf(

            Oracao("Xikombiso xa xihambano","","Hi xikombiso + xa xihambano,\n Nkulukumba + Hosi ya hina,\n hi huluxe + ka valala va hina.\n Hi vito la Bava, ni la Nwana + ni la Moya wa ku\n  Kwetsima. Ámen.", false),

            Oracao("A a twalisiwe","","A a twalisiwe Bava, ni Nwana ni Moya wa ku Kwetsima:\n  Tani hi leswi a nge xiswona a ku sunguleni, a swi ve tano ni\n  swoswi ni minkama hinkwayo. Ámen.", false),

            Oracao("Bava wa hina","","Bava wa hina u nga matilweni,\n  a vito la wena a li twalisiwe,\n  a wu te ka hina a nfumo wa wena,\n  ku rhandza ka wena a ku yentxiwe a misaveni\n tani hi le tilweni.\n  Hi nyike nyamuntlha swakudlha swa hina\n  swa siku ni siku.\n Hi rivalele swidjoho swa hina,\n tani hi leswi, na hina, hi va rivalelaka\n la'va hi djohelaka.\n  U nga hi tshiki hi wa mirigweni,\n  kambe hi ponise ka le'swa ku biha. Ámen.", false),

            Oracao("Xowani Maria","","Xowani, Maria, u tele hi timpswalo,\n  a Hosi yi na wena;\n  u katekile ka vavasati hinkwavo,\n  ni ye Nwana wa khwiri la Wena, Yesu, a katekile.\n  Maria wa ku basa, Mamana wa Nkulukumba,\n hi kombelele hina vadjohi,\n swoswi ni le nkameni wa ku fa ka hina. Ámen.", false),

            Oracao("Xikhongelo xa ku pfumela","","Nkulukumba wa mina, ndza kholwa swinene ka hinkwaswo\n  leswi Wena U nga hi byela swona, ni leswi a Kereke ya ku\n  Hlawuleka ya Katolika yi hi djondzisaka swona, hikusa a U\n phazami na swona a U hi kanganyisi.", false),

            Oracao("Xikhongelo xa ku tshemba","","Nkulukumba wa mina, ndza Ku tshemba hi leswi U nga ni\n  ntamu hinkwawo, ni wumbilu le'li nga heliki, ni ntiyiso ka\n  leswi U tshembisaka", false),

            Oracao("Xikhongelo xa lirhandzu","","Nkulukumba wa mina, ndza Ku rhandza hi mbilu ya mina\n  hinkwayo, hi leswi U nga munene ngopfu-ngopfu; hi kola\n  ka lirhandzu la Wena, ndzi rhandza munhu-kulorhi tani hi\n  leswi ndzi tirhandzaka xiswona.", false),

            Oracao("Xikhongelo xa ku vaviseka","","Xikwembu xa mina,\n ndza vaviseka hi ku Ku djohela,\n  hikusa Wena U munene ngopfu,\n  ni leswi ndzi ku rhandzaka hi mbilu ya mina hinkwayo\n  ndza tshembisa ku tilaya ni ku kala ndzi nga ha tlheli ndzi \n  Ku djohela hi ku pfuniwa hi timpswalo ta wena.\n  Ndza kombela na ndzi tshemba ku rivaleliwa swidjoho swa\n mina, hi wumbilu la Wena li nga likulu. Ámen.", false),

            Oracao("Xikhongelo xa ku vaviseka xa ku komanyana","","Nkulukumba Xikwembu xa mina,\n  ndza vaviseka ngopfu hi ku Ku djohela\n na U li munene ngopfu; Ndzi pfune a ku kala ndzi\n nga ha tlheli ndzi Ku djohela. Ámen.", false),

            Oracao("Mapfumelela ya vaapostolo","","Ndza pfumela ka Nkulukumba Bava wa ntamu\n  hinkwawo, Mutumbuluxi wa tilo ni misava.\n  Ndza pfumela ni ka Yesu Kristo, Nwana wa Yena wa\n  munwe, Hosi ya hina. Lweyi a nga hikiwa hi ntamu wa\n Moya wa ku Kwetsima. A velekiwa hi Nhwanyana Maria. A\n  xanisekile a nkameni wa Poncio Pilatos, a vambiwa a\n xihambanwini, a fa, a lahliwa, a va xikela ka vafi. A pfukile\n  ku feni hi siku la wunharhu, a thlatukela tilweni, a ya\n tshama a vokweni la xinene la Nkulukumba Bava wa ntamu\n hinkwawo. Laha a nga ta sukela kona loko a vuyela a ku ta\n  yavanyisa la'va ku hanya ni la'va ku fa.\n  Ndza pfumela ka Moya wa ku Kwetsima, ni ka Bandla la\n  ku hlawuleka la katolika, ni ku thlangana ka vahlawuleki,\n ni ku rivaleliwa ka swidjoho ni ku pfuka ka vafi, ni wutoni\n  le'li nga heliki. Ámen.", false),

            Oracao("Hi vavitaniwa","","Ha ku tlangela, Hosi, hi kola ka Vapadre U hi nyikeke.\n Hi vona va yavelaka a Xilalelo xa Miri wa Wena, a rivalelo la\n Wena, a Rito la Wena ni masacramento ya Wena.\n Kambe U vulile U ku: 'A usimui yikulu, kambe vatirhi i\n vatsongo'. Hakunene, macomunidade manyingi a ma na\n vapadre, hambi vairmã, kumbe varhangeli va ku ma tirhela.\n Vavabyi la'vo tala va faka na va nga ni ndlala ya wuswa la\n Rito la Wena, ni vadjohi la'vo tala va pfumalaka wa ku va\n yavela a rivalelo la Wena.\n Hosi, yendzela macomunidade ni mindjangu ya hina,\n U vitana la'vampshwa akuva va ta ve vapadre ni vairmã\n la'va lavekaka ka Kereke ya Wena. Tiyisa likholo la\n vapswali, akuva va nga pfaleli ndlela leyi vana va vona,\n kambe va swi kota ku va seketela ni ku va lwela.\n Ha ku kombela hi Kristo Nwana wa Wena kunwe ni ...\n Ámen.", false),

            Oracao("Persignar e benzer ","","Pelo Sinal + da Santa Cruz\nLivre-nos Deus  +  Nosso Senhor dos nossos  + inimigos.\nEm nome do Pai, e do Filho e do Espirito Santo. Ámen.", false),

            Oracao("Glória ao Pai ","","Glória ao Pai e ao Filho e ao Espírito Santo. Como era no\nprincipio, agora e sempre. Ámen.", false),

            Oracao("Pai Nosso","","Pai Nosso que estais no Céu,\nSantificado seja o Vosso Nome;\nVenha a nós o Vosso Reino\nSeja feita a Vossa Vontade,\nassim na terra como no Céu.\nO pão nosso de cada dia nos dai hoje.\nPerdoai-nos as nossas ofensas,\nAssim como nós perdoamos\na quem nos tem ofendido.\nE não nos deixeis cair em tentação.\nMas livrai-nos do mal. Ámen.", false),

            Oracao("Avé Maria","","Avé Maria, cheia de graça\no Senhor é convosco,\nbendita sois Vós entre as mulheres;\ne bendito é o fruto do Vosso ventre, Jesus.\nSanta Maria Mãe de Deus,\nrogai por nós pecadores,\nagora e na hora da nossa morte. Ámen", false),

            Oracao("Os Mandamentos da Lei da Deus","","1° Adorar a Deus e amá-lo sobre todas as coisas.\n \n2° Não invocar o santo nome de Deus em vão.\n \n3° Santificar os Domingos e Festas de guarda.\n \n4° Honrar pai e mãe (e outros legítimos superiores, false).\n \n5° Não matar (nem causar outro dano, no corpo, ou na alma, a si mesmo ou ao próximo, false).\n \n6° Guardar castidade nas palavras e nas obras.\n \n7° Não furtar (nem injustamente reter ou danificar os bens do próximo, false).\n \n8° Não levantar falsos testemunhos (nem de qualquer \n \n outro modo faltar à verdade ou difamar o próximo, false).\n \n9° Guardar castidade nos pensamentos e nos desejos.\n \n10° Não cobiçar as coisas alheias. \n \n \nEstes dez mandamentos encerram-se em dois que são:\n \n1° Amar a Deus sobre todas as coisas.sn\n \n2° E ao próximo como a nós mesmos.", false),

            Oracao("Os Mandamentos da Igreja","","1° Ouvir a missa inteira e abster-se de trabalhos servis nos domingos e festas de guarda.\n \n2° Confessar-se ao menos uma vez cada ano (se possível uma vez por mês, false).\n \n3° Comungar ao menos pela Páscoa da ressurreição (sempre que participar na missa, false).\n \n4° Guardar abstinência e jejuar nos dias determinados pela Igreja (Quarta-feira de cinzas e Sexta-feira Santa, e abstinência nas sextas-feiras durante a quaresma, false).\n \n5° Contribuir para as despesas do culto e para a sustentação do clero, segundo os legítimos usos e costumes e determinações da Igreja (pagar o dizimo, false).", false),

            Oracao("As Obras de misericórdia","","Corporais\n \n1° Dar de comera quem tem fome.\n \n2° Dar de beber a quem tem sede.\n \n3° Vestir os nus.\n \n4° Dar pousada aos peregrinos.\n \n5° Assistir aos enfermos. \n \n6° Visitar os presos. \n \n7° Enterrar os mortos.\n \n \nEspirituais\n \n1° Dar bom conselho. \n \n2° Ensinar os ignorantes. \n \n3° Corrigir os que erram. \n \n4° Consolar os tristes. \n \n5° Perdoar as injúrias.\n \n6° Sofrer com paciência as fraquezas do nosso próximo.\n \n7° Rogar a Deus por vivos e defuntos.", false),

            Oracao("Os Sacramentos","","1° Baptismo.\n\n2° Confirmação.\n\n3° Eucaristia\n\n4° Penitência.\n\n5° Unção dos enfermos.\n\n6° Orde\n\n7° Matrimónio.", false),

            Oracao("Os dons do Espirito Santo","","1° Sapiência.\n \n2° Entendimento.\n \n3° Conselho.\n \n4° Fortalez\n \n5° Ciência.\n \n6° Piedade.\n \n7° Temor de Deus.", false),

            Oracao("Acto De Fé","","Meu Deus, creio firmemente em tudo o que Vós revelastes e a Santa Igreja Católica nos ensina, porque não podeis enganar-Vos nem enganar-nos.", false),

            Oracao("Acto de Esperança","","Meu Deus, espero em Vós, porque sois omnipotente, infinitamente misericordioso e fidelíssimo às Vossas promessas.", false),

            Oracao("Acto de Caridade","","Meu Deus, amo-Vos de todo o meu coração, porque sois infinitamente bom, e por amor de Vós amo O próximo como a mim mesmo.", false),

            Oracao("Acto de Contrição","","Meu Deus, porque sois infinitamente bom, eu Vos amo de todo o meu coração, pesa-me de Vos ter ofendido, e, Com o auxilio da Vossa divina graça, Proponho firmemente emendar-me e nunca mais Vos tornar a ofender; peço e espero o perdão das minhas culpas pela Vossa infinita misericórdia. Ámen.", false),

            Oracao("Acto de Contrição (breve, false)","","Meu Deus, porque sois tão bom, tenho muita pena de Vos ter ofendido, ajudai-me a não tornar a pecar.\nÁmen.", false),

            Oracao("Credo",""," Creio em um só Deus, \n Pai todo-poderoso,\n criador do céu e da terra,\n de todas as coisas visíveis e invisíveis.\n Creio em um só Senhor, Jesus Cristo,\n Filho Unigénito de Deus,\n nascido do Pai antes de todos os séculos:\n Deus de Deus, Luz da Luz,\n Deus verdadeiro de Deus verdadeiro\n gerado, não criado, consubstancial ao Pai,\n por Ele todas as coisas foram feitas,\n e por nós, homens, e para nossa salvação\n desceu dos céus, (Faz-se inclinação, false)\n e encarnou pelo Espírito Santo,\n no seio da Virgem Maria e se fez homem. \n Também por nós foi crucificado\n sob Pôncio Pilatos,\n padeceu e foi sepultado.\n Ressuscitou ao terceiro dia,\n conforme as Escrituras;\n e subiu aos céus,\n onde está sentado à direita do Pai.\n De novo há-de vir em sua glória,\n para julgar os vivos e os mortos; \n e o seu reino não terá fim.\n Creio no Espírito Santo,\n Senhor que dá a vida,\n e procede do Pai e do Filho;\n e com o Pai e o Filho\n é adorado e glorificado:\n Ele que falou pelos Profetas.\n Creio na Igreja una, santa,\n católica e apostólica.\n Professo um só Baptismo\n para remissão dos pecados.\n E espero a ressurreição dos mortos,\n e a vida do mundo que há-de vir.\n Ámen.", false),

            Oracao("Salvé Rainha","","Salve, Rainha, Mãe de misericórdia, vida, doçura e esperança nossa, salve! A Vós bradamos os degradados filhos de Eva; a Vós suspiramos gemendo e chorando neste vale de lágrimas. Eia, pois, advogada nossa esses vossos olhos misericordiosos a nós volvei. E depois deste desterro nos mostrai Jesus, bendito fruto do Vosso ventre, ó clemente, ó piedosa, ó doce Virgem Maria!\n     V – Rogai por nós Santa Mãe de Deus:\n     R – Para que sejamos dignos das promessas de Cristo.\n     Ámen", false),

            Oracao("Ao Espirito Santo","","V - Vinde, Espirito Santo\nR - Enchei os corações dos vossos fiéis e acendei\nneles o fogo do vosso amor.\nV - Enviai, Senhor, o Vosso Espirito e tudo será criado,\nR - E renovareis a face da terra\n \nOREMOS:\n \nÓ Deus, que instruis os corações dos fiéis com as luzes do Espírito Santo, fazei que apreciemos rectamente todas as coisas segundo o mesmo Espírito e que gozemos sempre da Sua consolação. Por Cristo nosso Senhor.\nÁmen.\n\nou \n \nÓ Espirito Santo, Amor do Pai, e do Filho, inspirai-me sempre o que devo pensar, o que devo dizer, o que devo calar, o que devo escrever, como devo agir, o que devo fazer ,para promover a Vossa glória, o bem das almas e a minha própria santificação!", false),

            Oracao("Oração do Angelus","","V - O anjo do Senhor anunciou a Maria:\n \nR - E ela concebeu pelo poder do Espirito Santo.\n \nAve-Maria ...\n \nV - Eis a serva do Senhor: \n \nR - Faça-se em mim segundo a vossa palavra\n \nAve-Maria ...\n \nV - O Verbo Divino encarnou: \n \nR - E habitou entre nós.\n \nAve-Maria ...\n \nV - Rogai por nós, Santa Mãe de Deus.\n \nR - Para que sejamos dignos das promessas de Cristo\n \nOREMOS: Infundi, Senhor, a vossa graça em nossas almas, para que nós, que pela anunciação do Anjo conhecemos a Encarnação de Jesus Cristo vos Filho, pela sua Paixão e Morte na cruz e com a intercessão da bem-aventurada  Virgem Maria, alcancemos a glória da Ressurreição. Por Nosso Senhor Jesus Cristo. Ámen.", false),

            Oracao("Rainha do Céu","No tempo pascal","V - Rainha do Céu, alegrai-Vos, aleluia\n \nR - Porque aquele que merecestes trazer em Vosso seio, aleluia,\nRessuscitou como disse, aleluia.\nRogai a Deus por nós, aleluia.\n \nV - Exultai e alegrai-Vos, 6 Virgem Maria, aleluia.\n \nR - Porque o Senhor ressuscitou verdadeiramente, aleluia.\n \nOREMOS: Ó Deus, que Vos dignastes alegrar o mundo com a Ressurreição de vosso Filho Jesus Cristo, Senhor Nosso, concedei-nos, que, por Sua Mãe, a Virgem Maria, alcancemos as alegrias da vida eterna. Pelo mesmo Cristo Nosso Senhor.\nR - Ámen", false),

            Oracao("A Sagrada Família ","","Concedei-nos, Senhor Jesus, a graça de imitar o exemplo da Sagrada Família de Nazaré, para que,  na hora da nossa morte, assistidos pela Vossa Mãe e São José, mereçamos ser recebidos por Vós na felicidade eterna. Vós que sois Deus com o Pai na unidade do Espirito Santo. Ámen.\n \n•	Amado Jesus, José e Maria, o meu coração Vos dou e alma minha.\n \n•	Amado Jesus, José e Maria, assisti-me na última agonia,\n \n•	Amado Jesus, José e Maria, expire em paz entre Vos a minha alma.", false),

            Oracao("AS. José","","Ó Deus, nosso Pai, que no Vosso desígnio de salvação escolhestes S. José como esposo de Maria, Mãe do Vosso Filho, fazei que ele continue do céu a olhar por nós, que o veneremos como nosso protector.  Por Cristo nosso Senhor.\nÁmen.", false),

            Oracao("Antes das refeições","","Abençoai-nos, Senhor, e ao alimento que vamos tomar, Que ele repare as nossas forças,  para melhor vos servir e amar.\n \n•	Pai Nosso", false),

            Oracao("Depois das refeições","","Nós Vos agradecemos, Senhor,  o alimento que acabamos de receber. Dai-o também àqueles que não têm o necessário para cada dia, Ámen.", false),

            Oracao("Oferecimento das obras","","Ofereço-Vos, ó meu Deus, «em união com o Santíssimo Coração de Jesus e por meio do Coração Imaculado de Maria, as minhas orações, obras e sofrimentos deste dia, em reparação de todas as ofensas e por todas as intenções pelas quais o mesmo Divino  Coração está continuamente Intercedendo e sacrificando-Se nos nossos altares.\n     Eu Vo-las ofereço de modo particular pelas intenções do Apostolado da Oração neste  mês e neste dia,", false),

            Oracao("Ao Anjo da Guarda","","Santo Anjo do Senhor,  meu zeloso guardador, pois a ti me confiou a Piedade divina,  hoje e sempre me governa, rege, guarda e ilumina. Ámen.", false),

            Oracao("Antes de viajar","","Senhor, que encheis todos os lugares com a Vossa presença, acompanhai-me durante esta viagem,para que chegue ao meu destinoe volte a casa são e salvo.Que esta minha viagem seja umanúncio de alegria para todos aqueles que eu encontrar, uma mensagem deesperança e um testemunhode vida cristã. Ámen.\n•	Glória ao Pai...", false),

            Oracao("Do Catequista","","  Senhor! Vós me chamastes para  ser catequista (animador, false) nesta comunidade da Vossa Igreja. Confiastes-me a missão de anunciar a Vossa Palavra, de denunciar o  pecado e de testemunhar, pela própria vida, os valores do Santo Evangelho. Sei que é pesada a minha responsabilidade.Mas, Senhor, se me escolhestes, confio na Vossa graça.\nCaminharemos juntos, Senhor, apoiando-me em Vós e deixando-me iluminar pela vossa luz. \nQuero pôr-me inteiramente ao Vosso serviço: orientado pela Vossa Igreja   e a actualizando-me cada vez mais, servirei melhor o Vosso Povo. \nFazei de mim Vosso instrumento  para que chegue a todos o Vosso Reino, Reino de amor e  de paz, de fraternidade e de justiça, Reino onde Deus será tudo em todos. Ámen.", false),

            Oracao("Pelas vocações ","","  Senhor da messe e pastor do rebanho,  fazei ressoar em nossos ouvidos o vosso suave e forte convite: ‘vem e segue me'! \nDerramai sobre nós o Vosso Espirito: que Ele nos dê sabedoria para ver o caminho e generosidade para seguir a Vossa voz. \n  Senhor, que a messe não se perca por falta de operários. Ensinai a nossa vida a ser serviço. Fortalecei Os que querem Dedicar-se ao Reino, na vida consagrada e religiosa. \n  Senhor, que o rebanho não pereça por falta de pastores. Sustentai a fidelidade dos nossos bispos, padres e ministros. Dai perseverança aos nossos seminaristas. \n  Despertai coração dos nossos jovens para o ministério pastoral da Vossa Igreja. Fortalecei a fé dos nossos pais para que não impeçam, antes desejem e apoiem a vocação dos seus filhos. \n  Maria, . mãe da Igreja, modelo dos servidores do Evangelho, ajudai-nos a dizer 'sim'. Ámen.", false),

            Oracao("Esperança de Israel","","  Esperança de lsrael, Salvador nosso no tempo da aflição lançai sobre nós o Vosso olhar propício. Vede e visitai esta vinha, inundai de águas fecundas os seus sulcos, multiplicai os seus rebentos, tornai-a perfeita, Pois a vossa mão direita a plantou. Na verdade, a messe é grande, mas os operários são poucos. \n  Nós Vos rogamos, pois, Senhor da messe, que envieis operários para a Vossa messe. Multiplicai a família e fazei crescer a alegria para que sejam restaurados os muros de Jerusalém. \n  É Vossa esta casa! Senhor nosso Deus, é Vossa esta casa! Não haja nela nenhuma pedra que a Vossa mão não tenha colocado. Mas, aqueles que chamastes, guardai-as no Vosso Nome, e santificai-os na verdade. Ámen", false),

            Oracao("Pelos Sacerdotes","","  Jesus, Sacerdote Eterno, que destes à Igreja a missão de comunicar às almas os frutos redentores da Vossa Paixão e Morte, instituindo o Sacerdócio: humilde e fervorosamente Vos pedimos que abençoeis, santifiqueis e protejais os Vossos sacerdotes. \n  Defendei-os dos seus inimigos. Abrasai-os no Vosso amor e que o Vosso divino zelo se ateie neles, para maior eficácia do seu apostolado. \n  Nós Vos suplicamos que susciteis na Vossa Igreja muitas e santas vocações sacerdotais. Infundi naqueles, que por misericórdia escolhestes, os dons divinos de fé viva, esperança firme e caridade ardente. \n  Velai por eles, santificai os seus trabalhos, fazei-os dignos e santos apóstolos da vossa doutrina e da vossa vida. Assim seja. \n  Coração Sacerdotal de Jesus, santificai os sacerdotes.", false),

            Oracao("Oração dos Esposos e Pais","","  Senhor, Pai Santo, nós Vos damos graças e Vos damos bendizemos: criastes o homem e a mulher, abençoastes a sua união para que fossem ajuda e apoio um do outro, Recordai- Vos hoje de nós. \n  Nós temos a certeza de que habitais no nosso lar. Protegei-nos e fazei que o nosso amor seja dedicação e entrega, à imagem do amor de Cristo e da lgreja. \n  Sentimos obrigação de nos santificarmos no matrimónio e para isso Vos pedimos a graça de vivermos juntos durante uma longa vida, na alegria e na paz. \n  (Abençoai os filhos que nos destes, false). Santificai o nosso amor mutuo. Aceitai a oferta do nosso louvor e acção de graças. Ámen", false),

            Oracao("Consagração da Família a Nossa Senhora","","  Ó VIRGEM MARIA, consagramos hoje o nosso lar e todos osque nele habitam ao vosso Coração Imaculado.Que a nossa casa seja como a de Nazaré, uma morada depaz e de felicidade simples, pelo cumprimento da Vontade deDEUS, a prática da Caridade e o pleno abandono à DivinaProvidência.\n  velai sobre todos os que nela habitam; ajudai-os a viver sempre cristãmente; envolvei a todos de Maternal protecção e dignai-vos, na vossa bondade, ó Virgem Maria, reconstituir no Céu a nossa família da terra, consagrada para sempre ao vosso Coração Imaculado. Ámen", false),

            Oracao("Oração do Doente","","  Senhor, Pai Santo, que em Vosso Filho Jesus Cristo nos revelastes o sentido da saúde e da doença:\n  Conservai-me, pela Vossa graça, na certeza do Vosso amor e conduzi-me, através do sofrimento, à participação da Vossa eterna gloria.\n  Ajudai-me a participar na Paixão de Vosso Filho, suportando a minha doença e oferecendo-a pela paz do mundo e pela conversão dos pecadores.\n  Se for da Vossa Vontade, dai-me à saúde do corpo e fazei que ponha todas as minhas forças ao serviço dos meus irmãos, para glória da SS.ma Trindade.\n  Permanecei sempre comigo, para que eu viva na esperança da vinda de Cristo, Vosso Filho, que há-de transformar o meu corpo corruptível e torná-lo semelhante ao Seu Corpo de Gloria.\n  Isto Vos peço pela intercessão de Maria, minha Mãe.\nÁmen.", false),

            Oracao("A Nossa Senhora, Mãe dos Doentes","","Ó Maria, Virgem e Mãe Santíssima, que cuidastes com imensa ternura do Vosso amado Filho, Jesus, velai por todos os outros Vossos filhos doentes e necessitados de amor, que enchem o mundo. Lembrai-vos, sobretudo:\n  •	dos que, a esta hora, já não têm conhecimento e vão morrer;\n  •	dos que entram em agonia,\n  •	dos que já abandonaram toda a esperança de cura,\n  •	dos que gritam e choram com dor;\n  •	dos que morrem aos poucos por falta de alimento,\n  •	dos que tanto desejam andar e se vêem imobilizados\n  •	dos que se vêm forçados a trabalhar sem poder,\n  •	dos que passam longas noites cobertos de lágrimas dormir;\n  •	dos que já não crêem numa vida melhor;\n  •	dos que tudo maldizem e se revoltam contra Deus\n  •	dos que ignoram Jesus Cristo e o seu amor por eles. \n   Olhai, também, Senhora, para as minhas necessidades, e curai-me de todos os males, por amor ao Vosso Filho. Ámen", false),

            Oracao("Senhor, ajudai-me a não ser egoísta","","  •	Senhor,\n  •	ajudai-me a não ser egoísta\n  •	ajudai-me a não querer ser feliz sozinho.\n  •	Vejo crianças que choram com fome e dormem no chão.\n  •	vejo barracas sem água, sem esgotos, sem luz.\n  •	Vejo multidões de homens desempregados,\n  •	Vejo jovens impossibilitados de estudar,\n  •	Vejo ódio, violência, opressão, marginalização.\n  •	Vejo lares sem amor, lares destruídos,\n  •	Vejo a exploração do sexo, da pornografia.\n  •	Vejo exaltar a técnica, o dinheiro, e vejo a pessoa humana esmagada.\n   Não! Não tenho direito de ser feliz sozinho.\n   Senhor, convosco quero construir um mundo novo, onde haja menos ódio e mais amor, menos desigualdade e mais Justiça, menos egoísmo e mais doação, menos desunião mais fraternidade, menos opressão e mais liberdade: a verdadeira liberdade dos filhos de Deus. Ámen.", false)

        )

        viewModelScope.launch {
            oracoesParaInserir.forEach { dao.upsertOracao(it) }
        }
    }

    fun onEvent(event: OracoesEvent){
        when(event){
            is OracoesEvent.SaveOracao -> {
                val oracao = Oracao(
                    titulo = state.value.titulo.value,
                    subTitulo = state.value.subTitulo.value,
                    corpo = state.value.corpo.value,
                    favorito = state.value.favorito.value
                )
                viewModelScope.launch { dao.upsertOracao(oracao) }
            }

            is OracoesEvent.UpdateFavorito -> {
                viewModelScope.launch {
                    // Obtém a Cancao pelo ID
                    val cancao = dao.getOracaoById(event.oracaoId)
                    // Verifica se a Cancao não é nula e atualiza o valor de favorito
                    cancao?.let {
                        val novaCancao = it.copy(favorito = event.novoFavorito)
                        dao.upsertOracao(novaCancao)
                    }
                }
            }
        }

    }
}